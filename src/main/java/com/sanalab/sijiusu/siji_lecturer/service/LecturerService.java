package com.sanalab.sijiusu.siji_lecturer.service;

import com.sanalab.sijiusu.core.converter.CourseSectionConverter;
import com.sanalab.sijiusu.core.database.repository.CourseSectionRepository;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final CourseSectionRepository courseSectionRepository;

    @Autowired
    public LecturerService(LecturerRepository lecturerRepository, CourseSectionRepository courseSectionRepository) {
        this.lecturerRepository = lecturerRepository;
        this.courseSectionRepository = courseSectionRepository;
    }

    public AdminUsersController.LecturerDto getCurrentLecturer() {
        Long lecturerId;
        try {
            lecturerId = (Long) org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        } catch (ClassCastException e) {
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid authentication context");
        }

        var lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Lecturer not found")
        );

        return LecturerConverter.toDto(lecturer);
    }

    @Transactional
    public void enrollLecturerToSection(Long sectionId) {
        Long lecturerId;
        try {
            lecturerId = (Long) org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        } catch (ClassCastException e) {
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid authentication context");
        }

        var lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Lecturer not found")
        );

        var section = courseSectionRepository.findById(sectionId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Section not found")
        );

        if (section.getLecturer() != null) {
            throw responseException(HttpStatus.CONFLICT, "Section already has a lecturer assigned");
        }

        section.setLecturer(lecturer);

        courseSectionRepository.save(section);
    }

    public List<AdminUsersController.CourseSectionTakenDto> getAvailableSections(String courseName) {
        Long lecturerId;
        try {
            lecturerId = (Long) org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        } catch (ClassCastException e) {
            throw responseException(HttpStatus.UNAUTHORIZED, "Invalid authentication context");
        }

        var lecturer = lecturerRepository.findById(lecturerId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Lecturer not found")
        );

        var majorId = lecturer.getDepartment().getId();
        if(courseName == null || courseName.isBlank()) {
            var sections = courseSectionRepository.findAllByCourse_Major_Id(majorId)
                .stream()
                .filter(section -> section.getLecturer() == null)
                .map(CourseSectionConverter::toSectionTakenDto)
                .toList();

            sections.forEach(section -> System.out.println(section.lecturer()));

            return sections;
        }

        return courseSectionRepository.findAllByCourse_Major_IdAndCourse_NameContainingIgnoreCase(majorId, courseName)
            .stream()
            .filter(section -> section.getLecturer() == null || !section.getLecturer().getId().equals(lecturerId))
            .map(CourseSectionConverter::toSectionTakenDto)
            .toList();
    }
}