package com.sanalab.sijiusu.core.database.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, name = "faculty_code")
    private String facultyCode;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Major> departments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public List<Major> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Major> departments) {
        this.departments = departments;
    }
}
