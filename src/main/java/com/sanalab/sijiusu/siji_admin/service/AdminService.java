package com.sanalab.sijiusu.siji_admin.service;

import com.sanalab.sijiusu.auth.data.Role;
import com.sanalab.sijiusu.auth.database.repository.UserRepository;
import com.sanalab.sijiusu.core.component.HashEncoder;
import com.sanalab.sijiusu.ext.database.model.Faculty;
import com.sanalab.sijiusu.ext.database.model.Major;
import com.sanalab.sijiusu.ext.database.repository.FacultyRepository;
import com.sanalab.sijiusu.ext.database.repository.MajorRepository;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import com.sanalab.sijiusu.siji_student.database.model.Student;
import com.sanalab.sijiusu.siji_student.database.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final HashEncoder hashEncoder;
    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;


    @Autowired
    public AdminService(
        UserRepository userRepository,
        LecturerRepository lecturerRepository,
        StudentRepository studentRepository,
        HashEncoder hashEncoder,
        MajorRepository majorRepository,
        FacultyRepository facultyRepository
    ) {
        this.userRepository = userRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.hashEncoder = hashEncoder;
        this.majorRepository = majorRepository;
        this.facultyRepository = facultyRepository;
    }

    public void addStudent(
        String name,
        String email,
        String password,
        String nim,
        Long majorId,
        Long academicAdvisorId
    ) {
        // Validate the payload
        if (name == null || email == null || password == null ||
            nim == null || majorId == null || academicAdvisorId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the email already exists
        if (userRepository.existsByEmail(email)) {
            throw responseException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Check if the major exists
        var major = majorRepository.findById(majorId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Major not found")
        );

        // Check if the academic advisor exists
        var academicAdvisor = lecturerRepository.findById(academicAdvisorId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Academic advisor not found")
        );

        // Create a new student entity and save it to the database
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPasswordHashed(hashEncoder.encode(password));
        student.setNim(nim);
        student.setMajor(major);
        student.setAcademicAdvisor(academicAdvisor);
        student.setRole(Role.Student);

        studentRepository.save(student);
    }

    public void addLecturer(
        String name,
        String email,
        String password,
        String nip,
        String nidn,
        Long departmentId
    ) {
        // Validate the payload
        if (name == null || email == null || password == null ||
            nip == null || nidn == null || departmentId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the email already exists
        if (userRepository.existsByEmail(email)) {
            throw responseException(HttpStatus.CONFLICT, "Email already exists");
        }

        var department = majorRepository.findById(departmentId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Department not found")
        );

        // Create a new lecturer entity and save it to the database
        Lecturer lecturer = new Lecturer();
        lecturer.setName(name);
        lecturer.setEmail(email);
        lecturer.setPasswordHashed(hashEncoder.encode(password));
        lecturer.setNip(nip);
        lecturer.setNidn(nidn);
        lecturer.setDepartment(department);
        lecturer.setRole(Role.Lecturer);

        lecturerRepository.save(lecturer);
    }

    public void addFaculty(String name, String facultyCode) {
        // Validate the payload
        if (name == null || facultyCode == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the code already exists
        if (facultyRepository.existsByFacultyCode(facultyCode)) {
            throw responseException(HttpStatus.CONFLICT, "Code already exists");
        }

        // Create a new faculty entity and save it to the database
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setFacultyCode(facultyCode);

        facultyRepository.save(faculty);
    }

    public void addMajor(String name, Long facultyId, String majorCode) {
        // Validate the payload
        if (name == null || facultyId == null || majorCode == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the code already exists
        if (majorRepository.existsByMajorCode(majorCode)) {
            throw responseException(HttpStatus.CONFLICT, "Code already exists");
        }

        // Check if the faculty exists
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Faculty not found")
        );

        // Create a new major entity and save it to the database
        Major major = new Major();
        major.setName(name);
        major.setMajorCode(majorCode);
        major.setFaculty(faculty);

        majorRepository.save(major);
    }
}
