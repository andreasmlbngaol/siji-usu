package com.sanalab.sijiusu.siji_admin.service;

import com.sanalab.sijiusu.auth.data.Role;
import com.sanalab.sijiusu.auth.database.repository.UserRepository;
import com.sanalab.sijiusu.core.component.HashEncoder;
import com.sanalab.sijiusu.core.database.model.*;
import com.sanalab.sijiusu.core.database.repository.*;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import com.sanalab.sijiusu.siji_student.database.model.Student;
import com.sanalab.sijiusu.siji_student.database.repository.StudentRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminService {
    private final HashEncoder hashEncoder;
    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final MajorRepository majorRepository;
    private final RoomRepository roomRepository;
    private final CourseRepository courseRepository;
    private final CourseSectionRepository sectionRepository;


    @Autowired
    public AdminService(
        HashEncoder hashEncoder,
        UserRepository userRepository,
        LecturerRepository lecturerRepository,
        StudentRepository studentRepository,
        FacultyRepository facultyRepository,
        MajorRepository majorRepository,
        RoomRepository roomRepository,
        CourseRepository courseRepository,
        CourseSectionRepository sectionRepository
    ) {
        this.hashEncoder = hashEncoder;
        this.userRepository = userRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.majorRepository = majorRepository;
        this.roomRepository = roomRepository;
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
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
        if (majorRepository.existsByMajorCodeAndFacultyId(majorCode, facultyId)) {
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

    public void addRoom(String name, Long departmentId) {
        // Validate the payload
        if (name == null || departmentId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the room already exists
        if (roomRepository.existsByNameAndDepartmentId(name, departmentId)) {
            throw responseException(HttpStatus.CONFLICT, "Room already exists");
        }

        // Check if the department exists
        Major department = majorRepository.findById(departmentId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Department not found")
        );

        // Create a new room entity and save it to the database
        var room = new Room();
        room.setName(name);
        room.setDepartment(department);

        roomRepository.save(room);
    }

    public void addCourse(
        String name,
        String courseCode,
        Long majorId
    ) {
        // Validate the payload
        if (name == null || courseCode == null || majorId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the code already exists
        if (courseRepository.existsByCourseCode(courseCode)) {
            throw responseException(HttpStatus.CONFLICT, "Code already exists");
        }

        // Check if the major exists
        Major major = majorRepository.findById(majorId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Major not found")
        );

        // Create a new course entity and save it to the database
        var course = new Course();
        course.setName(name);
        course.setCourseCode(courseCode);
        course.setMajor(major);

        courseRepository.save(course);
    }

    public void addCourseSection(
        String name,
        Long courseId,
        @Nullable Long lecturerId,
        @Nullable Long roomId
    ) {
        // Validate the payload
        if (name == null || courseId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the course exists
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Course not found")
        );

        // Check if the lecturer exists
        Lecturer lecturer = null;
        if (lecturerId != null) {
            lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
                responseException(HttpStatus.NOT_FOUND, "Lecturer not found")
            );
        }

        // Check if the room exists
        Room room = null;
        if (roomId != null) {
            room = roomRepository.findById(roomId).orElseThrow(() ->
                responseException(HttpStatus.NOT_FOUND, "Room not found")
            );
        }

        // Create a new course section entity and save it to the database
        var section = new CourseSection();
        section.setName(name);
        section.setCourse(course);
        section.setLecturer(lecturer);
        section.setRoom(room);

        sectionRepository.save(section);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
