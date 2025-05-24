package com.sanalab.sijiusu.siji_admin.users.service;

import com.sanalab.sijiusu.auth.data.Role;
import com.sanalab.sijiusu.auth.database.repository.UserRepository;
import com.sanalab.sijiusu.core.component.HashEncoder;
import com.sanalab.sijiusu.core.database.repository.MajorRepository;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import com.sanalab.sijiusu.siji_student.database.model.Student;
import com.sanalab.sijiusu.siji_student.database.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminUsersService {
    private final HashEncoder hashEncoder;
    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;

    @Autowired
    public AdminUsersService(
        HashEncoder hashEncoder,
        UserRepository userRepository,
        LecturerRepository lecturerRepository,
        StudentRepository studentRepository,
        MajorRepository majorRepository
    ) {
        this.hashEncoder = hashEncoder;
        this.userRepository = userRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
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

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
