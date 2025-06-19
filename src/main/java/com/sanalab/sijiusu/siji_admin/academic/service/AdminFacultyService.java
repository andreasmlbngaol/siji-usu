package com.sanalab.sijiusu.siji_admin.academic.service;

import com.sanalab.sijiusu.core.converter.FacultyConverter;
import com.sanalab.sijiusu.core.database.model.Faculty;
import com.sanalab.sijiusu.core.database.repository.FacultyRepository;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminFacultyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminFacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public AdminFacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
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

    public List<AdminFacultyController.FacultyDto> getAllFaculties() {
        return facultyRepository.findAllByOrderById()
            .stream()
            .map(FacultyConverter::toDto)
            .toList();
    }

    public AdminFacultyController.FacultyDto getFacultyById(Long id) {
        return facultyRepository.findById(id)
            .map(FacultyConverter::toDto)
            .orElseThrow(() -> responseException(HttpStatus.NOT_FOUND, "Faculty not found"));
    }

    public void updateFaculty(Long id, String name, String facultyCode) {
        Faculty faculty = facultyRepository.findById(id)
            .orElseThrow(() -> responseException(HttpStatus.NOT_FOUND, "Faculty not found"));

        if(name != null) {
            faculty.setName(name);
        }

        if(facultyCode != null) {
            if (facultyRepository.existsByFacultyCode(facultyCode)) {
                throw responseException(HttpStatus.CONFLICT, "Code already exists");
            }
            faculty.setFacultyCode(facultyCode);
        }

        facultyRepository.save(faculty);
    }
}
