package com.sanalab.sijiusu.siji_admin.academic.service;

import com.sanalab.sijiusu.core.database.model.Faculty;
import com.sanalab.sijiusu.core.database.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

}
