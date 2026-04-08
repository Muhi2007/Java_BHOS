package com.platform.online_course.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

@Entity
public class Teacher extends BaseUser{
    @ElementCollection
    private Set<Long> createdCourseIDs;

    private String department;
    // ------------------------------------------------------------ //
    
    public Teacher() {
        super();
        this.createdCourseIDs = new HashSet<>();
    }

    public Teacher(String username, String password, String email) {
        super(username, password, email);
        this.createdCourseIDs = new HashSet<>();
    }

    public Set<Long> getCreatedCourseIDs() {
        return createdCourseIDs;
    }

    public void setCreatedCourseIDs(Set<Long> createdCourseIDs) {
        this.createdCourseIDs = createdCourseIDs;
    }

    // Backward-compatible aliases for older call sites.
    public Set<Long> getCreatedCouseIDs() {
        return getCreatedCourseIDs();
    }

    public void setCreatedCouseIDs(Set<Long> createdCouseIDs) {
        setCreatedCourseIDs(createdCouseIDs);
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String getDashboardURL() {
        return "/teacher/dashboard";
    }

    @Override
    public String getRole() {
        return "Teacher";
    }
}
