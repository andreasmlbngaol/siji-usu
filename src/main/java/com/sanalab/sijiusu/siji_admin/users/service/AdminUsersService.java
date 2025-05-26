package com.sanalab.sijiusu.siji_admin.users.service;

import com.sanalab.sijiusu.auth.data.Role;
import com.sanalab.sijiusu.auth.database.repository.UserRepository;
import com.sanalab.sijiusu.auth.service.UserConverter;
import com.sanalab.sijiusu.core.component.HashEncoder;
import com.sanalab.sijiusu.core.database.repository.MajorRepository;
import com.sanalab.sijiusu.siji_admin.database.repository.AdminRepository;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminController;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import com.sanalab.sijiusu.siji_lecturer.service.LecturerConverter;
import com.sanalab.sijiusu.siji_student.database.model.Student;
import com.sanalab.sijiusu.siji_student.database.repository.StudentRepository;
import com.sanalab.sijiusu.siji_student.service.StudentConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminUsersService {
    private final HashEncoder hashEncoder;
    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminUsersService(
        HashEncoder hashEncoder,
        UserRepository userRepository,
        LecturerRepository lecturerRepository,
        StudentRepository studentRepository,
        MajorRepository majorRepository,
        AdminRepository adminRepository) {
        this.hashEncoder = hashEncoder;
        this.userRepository = userRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
        this.adminRepository = adminRepository;
    }

    public void addStudent(
        String name,
        String email,
        String password,
        String nim,
        Long majorId,
        Long academicAdvisorId,
        Integer year
    ) {
        // Validate the payload
        if (name == null || email == null || password == null ||
            nim == null || majorId == null || academicAdvisorId == null || year == null) {
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
        student.setYear(year);

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

    public List<AdminUsersController.StudentDto> getAllStudents() {
        return studentRepository.findAll()
            .stream()
            .map(StudentConverter::toDto)
            .toList();
    }

    public AdminUsersController.StudentDto getStudentById(Long id) {
        var student = studentRepository.findById(id).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Student not found")
        );
        return StudentConverter.toDto(student);
    }

    public List<AdminUsersController.StudentDto> getStudentsByNameLike(String name) {
        return studentRepository.findAllByNameContainingIgnoreCase(name)
            .stream()
            .map(StudentConverter::toDto)
            .toList();
    }

    @Transactional
    public void updateStudent(
        Long id,
        String name,
        String email,
        String nim,
        Long academicAdvisorId
    ) {
        // Check if the student exists
        var student = studentRepository.findById(id).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Student not found")
        );
        if(name != null && !name.isBlank()) student.setName(name);
        if(email != null && !email.isBlank()) {
            if (userRepository.existsByEmail(email))
                throw responseException(HttpStatus.CONFLICT, "Email already exists");
            student.setEmail(email);
        }
        if(nim != null && !nim.isBlank()) {
            if(studentRepository.existsByNim(nim))
                throw responseException(HttpStatus.CONFLICT, "NIM already exists");
            student.setNim(nim);
        }
        if(academicAdvisorId != null) {
            var academicAdvisor = lecturerRepository.findById(academicAdvisorId).orElseThrow(() ->
                responseException(HttpStatus.NOT_FOUND, "Academic advisor not found")
            );
            student.setAcademicAdvisor(academicAdvisor);
        }

        student.setUpdatedAt(Instant.now());
        studentRepository.save(student);
    }

    public List<AdminUsersController.LecturerDto> getAllLecturers() {
        return lecturerRepository.findAll()
            .stream()
            .map(LecturerConverter::toDto)
            .toList();
    }

    public AdminUsersController.LecturerDto getLecturerById(Long id) {
        var teacher = lecturerRepository.findById(id).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Lecturer not found")
        );
        return LecturerConverter.toDto(teacher);
    }

    public List<AdminUsersController.LecturerDto> getLecturersByNameLike(String name) {
        return lecturerRepository.findAllByNameContainingIgnoreCase(name)
            .stream()
            .map(LecturerConverter::toDto)
            .toList();
    }

    @Transactional
    public void updateLecturer(
        Long id,
        String name,
        String email,
        String nip,
        String nidn
    ) {
        // Check if the lecturer exists
        var lecturer = lecturerRepository.findById(id).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Lecturer not found")
        );

        if(name != null && !name.isBlank()) lecturer.setName(name);
        if(email != null && !email.isBlank()) {
            if (userRepository.existsByEmail(email))
                throw responseException(HttpStatus.CONFLICT, "Email already exists");
            lecturer.setEmail(email);
        }
        if(nip != null && !nip.isBlank()) {
            if(lecturerRepository.existsByNip(nip))
                throw responseException(HttpStatus.CONFLICT, "NIP already exists");
            lecturer.setNip(nip);
        }
        if(nidn != null && !nidn.isBlank()) {
            if(lecturerRepository.existsByNidn(nidn))
                throw responseException(HttpStatus.CONFLICT, "NIDN already exists");
            lecturer.setNidn(nidn);
        }

        lecturer.setUpdatedAt(Instant.now());
        lecturerRepository.save(lecturer);
    }

    public List<AdminUsersController.UserDto> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(UserConverter::toDto)
            .toList();
    }
    public AdminUsersController.UserDto getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "User not found")
        );
        return UserConverter.toDto(user);
    }

    public AdminController.AdminDto getCurrentAdmin() {
        Long ownerId = (Long) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        var owner = adminRepository.findById(ownerId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Admin not found")
        );

        return AdminConverter.toDto(owner);
    }
}
