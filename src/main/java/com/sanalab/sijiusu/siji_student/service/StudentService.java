package com.sanalab.sijiusu.siji_student.service;

import com.sanalab.sijiusu.core.converter.CourseSectionConverter;
import com.sanalab.sijiusu.core.database.model.CourseSection;
import com.sanalab.sijiusu.core.database.repository.CourseSectionRepository;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_student.database.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseSectionRepository courseSectionRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseSectionRepository courseSectionRepository) {
        this.studentRepository = studentRepository;
        this.courseSectionRepository = courseSectionRepository;
    }

    @Transactional
    public void enrollStudentToSection(Long sectionId) {
        Long studentId;
        try {
            studentId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        } catch (ClassCastException e) {
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid authentication context");
        }

        var student = studentRepository.findById(studentId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Student not found")
        );

        var section = courseSectionRepository.findById(sectionId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Section not found")
        );

        if (section.getStudents().contains(student)) {
            throw responseException(HttpStatus.CONFLICT, "Student already enrolled in this section");
        }

        section.getStudents().add(student);

        courseSectionRepository.save(section);
    }

    public AdminUsersController.StudentDto getCurrentStudent() {
        Long studentId;
        try {
            studentId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        } catch (ClassCastException e) {
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid authentication context");
        }

        var student = studentRepository.findById(studentId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Student not found")
        );

        return StudentConverter.toDto(student);
    }

    public List<AdminUsersController.CourseSectionTakenDto> getAvailableSections(String courseName) {
        Long studentId;
        try {
            studentId = (Long) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        } catch (ClassCastException e) {
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid authentication context");
        }

        var majorId = studentRepository.findById(studentId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Student not found")
        ).getMajor().getId();

        List<CourseSection> sections;
        if (courseName == null || courseName.isBlank()) {
            sections =  courseSectionRepository.findAllByCourse_Major_Id(majorId);
        } else {
            sections =  courseSectionRepository.findAllByCourse_Major_IdAndCourse_NameContainingIgnoreCase(majorId, courseName);
        }

        return sections.stream()
            .map(CourseSectionConverter::toSectionTakenDto)
            .toList();
    }
}
