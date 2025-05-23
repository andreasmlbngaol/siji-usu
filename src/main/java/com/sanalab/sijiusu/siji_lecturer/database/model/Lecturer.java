package com.sanalab.sijiusu.siji_lecturer.database.model;

import com.sanalab.sijiusu.auth.database.model.User;
import com.sanalab.sijiusu.core.database.model.CourseSection;
import com.sanalab.sijiusu.core.database.model.Major;
import com.sanalab.sijiusu.siji_student.database.model.Student;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity(name = "lecturers")
public class Lecturer extends User {
    @Column(nullable = false, unique = true)
    private String nip;

    @Column(nullable = false, unique = true)
    private String nidn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Major department;

    @OneToMany(mappedBy = "lecturer", fetch = FetchType.LAZY)
    private List<CourseSection> courseSections = new ArrayList<>();

    @OneToMany(mappedBy = "academicAdvisor", fetch = FetchType.LAZY)
    private List<Student> advisedStudents = new ArrayList<>();

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public Major getDepartment() {
        return department;
    }

    public void setDepartment(Major department) {
        this.department = department;
    }

    public List<CourseSection> getCourseSections() {
        return courseSections;
    }

    public void setCourseSections(List<CourseSection> courseSections) {
        this.courseSections = courseSections;
    }

    public List<Student> getAdvisedStudents() {
        return advisedStudents;
    }

    public void setAdvisedStudents(List<Student> advisedStudents) {
        this.advisedStudents = advisedStudents;
    }
}
