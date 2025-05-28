package com.sanalab.sijiusu.siji_lecturer.controller;

import com.sanalab.sijiusu.core.util.Routing;
import com.sanalab.sijiusu.siji_admin.users.controller.AdminUsersController;
import com.sanalab.sijiusu.siji_lecturer.service.LecturerService;
import com.sanalab.sijiusu.siji_student.controller.StudentController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Routing.LECTURERS)
public class LecturerController {

    private final LecturerService lecturerService;

    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping
    public AdminUsersController.LecturerDto getCurrentLecturer() {
        return lecturerService.getCurrentLecturer();
    }

    @PostMapping(Routing.COURSES + Routing.SECTIONS)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enrollToSection(
        @Valid @RequestBody StudentController.EnrollToSectionPayload payload
    ) {
        lecturerService.enrollLecturerToSection(payload.sectionId());
    }

    @GetMapping(Routing.COURSES + Routing.SECTIONS)
    public List<AdminUsersController.CourseSectionTakenDto> getAvailableSections(
        @RequestParam(name = "query", required = false, defaultValue = "") String query
    ) {
        return lecturerService.getAvailableSections(query);
    }

}
