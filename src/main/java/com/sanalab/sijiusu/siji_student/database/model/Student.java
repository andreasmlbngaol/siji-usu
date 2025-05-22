package com.sanalab.sijiusu.siji_student.database.model;

import com.sanalab.sijiusu.auth.database.model.User;
import com.sanalab.sijiusu.ext.database.model.CourseSection;
import com.sanalab.sijiusu.ext.database.model.Major;
import com.sanalab.sijiusu.siji_lecturer.database.model.Lecturer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "students")
public class Student extends User {
    @Column(nullable = false, unique = true)
    private String nim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    @ManyToMany(mappedBy = "students")
    private List<CourseSection> courseSections = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_advisor_id")
    private Lecturer academicAdvisor;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public List<CourseSection> getCourseSections() {
        return courseSections;
    }

    public void setCourseSections(List<CourseSection> courseSections) {
        this.courseSections = courseSections;
    }

    public Lecturer getAcademicAdvisor() {
        return academicAdvisor;
    }

    public void setAcademicAdvisor(Lecturer academicAdvisor) {
        this.academicAdvisor = academicAdvisor;
    }
}
