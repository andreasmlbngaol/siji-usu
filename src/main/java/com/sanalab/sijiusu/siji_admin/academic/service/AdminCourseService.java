package com.sanalab.sijiusu.siji_admin.academic.service;

import com.sanalab.sijiusu.core.converter.CourseConverter;
import com.sanalab.sijiusu.core.database.model.Course;
import com.sanalab.sijiusu.core.database.model.CourseSection;
import com.sanalab.sijiusu.core.database.model.Major;
import com.sanalab.sijiusu.core.database.model.Room;
import com.sanalab.sijiusu.core.database.repository.CourseRepository;
import com.sanalab.sijiusu.core.database.repository.CourseSectionRepository;
import com.sanalab.sijiusu.core.database.repository.MajorRepository;
import com.sanalab.sijiusu.core.database.repository.RoomRepository;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminCourseController;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import com.sanalab.sijiusu.siji_lecturer.database.repository.LecturerRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminCourseService {
    private final CourseRepository courseRepository;
    private final MajorRepository majorRepository;
    private final LecturerRepository lecturerRepository;
    private final RoomRepository roomRepository;
    private final CourseSectionRepository sectionRepository;

    @Autowired
    public AdminCourseService(
        CourseRepository courseRepository,
        MajorRepository majorRepository,
        LecturerRepository lecturerRepository,
        RoomRepository roomRepository,
        CourseSectionRepository sectionRepository
    ) {
        this.courseRepository = courseRepository;
        this.majorRepository = majorRepository;
        this.lecturerRepository = lecturerRepository;
        this.roomRepository = roomRepository;
        this.sectionRepository = sectionRepository;
    }

    public void addCourse(
        String name,
        Long majorId
    ) {
        // Validate the payload
        if (name == null ||  majorId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the major exists
        Major major = majorRepository.findById(majorId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Major not found")
        );

        // Create a new course entity and save it to the database
        var course = new Course();
        course.setName(name);
        course.setMajor(major);

        courseRepository.save(course);
    }

    public List<AdminCourseController.CourseDto> getCoursesByMajor(Long majorId) {
        // Validate the majorId
        if (majorId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the major exists
        Major major = majorRepository.findById(majorId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Major not found")
        );

        return major.getCourses()
            .stream()
            .map(CourseConverter::toDto)
            .toList();
    }

    public List<AdminCourseController.CourseDto> getCoursesByMajorAndName(Long majorId, String name) {
        // Validate the majorId and name
        if (majorId == null || name == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        var courses = courseRepository.findAllByMajor_IdAndNameContainingIgnoreCase(majorId, name);
        return courses.stream()
            .map(CourseConverter::toDto)
            .toList();

    }

    public AdminCourseController.CourseDto getCourseById(Long courseId) {
        // Validate the courseId
        if (courseId == null) {
            throw responseException(HttpStatus.BAD_REQUEST, "Missing required fields");
        }

        // Check if the course exists
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Course not found")
        );

        // Convert the course to DTO
        return CourseConverter.toDto(course);
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
}
