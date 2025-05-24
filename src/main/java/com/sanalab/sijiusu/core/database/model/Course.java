package com.sanalab.sijiusu.core.database.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JoinColumn(name = "major_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Major major;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<CourseSection> courseSections = new ArrayList<>();

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

    public List<CourseSection> getCourseSections() {
        return courseSections;
    }

    public void setCourseSections(List<CourseSection> courseSections) {
        this.courseSections = courseSections;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
