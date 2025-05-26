package com.sanalab.sijiusu.siji_admin.academic.service;

import com.sanalab.sijiusu.core.converter.MajorConverter;
import com.sanalab.sijiusu.core.database.model.Faculty;
import com.sanalab.sijiusu.core.database.model.Major;
import com.sanalab.sijiusu.core.database.repository.FacultyRepository;
import com.sanalab.sijiusu.core.database.repository.MajorRepository;
import com.sanalab.sijiusu.siji_admin.academic.controller.AdminMajorController;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sanalab.sijiusu.core.util.ResponseHelper.responseException;

@Service
public class AdminMajorService {

    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;

    @Autowired
    public AdminMajorService(MajorRepository majorRepository, FacultyRepository facultyRepository) {
        this.majorRepository = majorRepository;
        this.facultyRepository = facultyRepository;
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

    public List<AdminMajorController.MajorDto> getAllMajors() {
        return majorRepository.findAll()
            .stream()
            .map(MajorConverter::toDto)
            .toList();
    }

    public List<AdminMajorController.MajorDto> getMajorsByNameLike(String name) {
        if (name == null || name.isBlank()) {
            throw responseException(HttpStatus.BAD_REQUEST, "Name cannot be null or blank");
        }

        List<Major> majors = majorRepository.findByNameContainingIgnoreCase(name);
        if (majors.isEmpty()) {
            throw responseException(HttpStatus.NOT_FOUND, "No majors found with the given name");
        }

        return majors.stream()
            .map(MajorConverter::toDto)
            .toList();
    }

    public AdminMajorController.MajorDto getMajorById(Long majorId) {
        return majorRepository.findById(majorId)
            .map(MajorConverter::toDto)
            .orElseThrow(() -> responseException(HttpStatus.NOT_FOUND, "Major not found"));
    }

    @Transactional
    public void updateMajor(Long id, String name, String majorCode) {
        Major major = majorRepository.findById(id).orElseThrow(() ->
            responseException(HttpStatus.NOT_FOUND, "Major not found")
        );

        if (name != null && !name.isBlank()) {
            major.setName(name);
        }

        if (majorCode != null && !majorCode.isBlank()) {
            if (majorRepository.existsByMajorCodeAndFacultyId(majorCode, major.getFaculty().getId())) {
                throw responseException(HttpStatus.CONFLICT, "Code already exists");
            }
            major.setMajorCode(majorCode);
        }

        majorRepository.save(major);
    }
}
