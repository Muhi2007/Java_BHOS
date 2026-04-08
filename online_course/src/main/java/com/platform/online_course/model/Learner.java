package com.platform.online_course.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Learner extends BaseUser{
    @ElementCollection
    private Set<Long> enrolledCourseIDs;

    @ElementCollection
    private Set<Long> finishedCourseIDs;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<QuizAttempt> myAttempts = new ArrayList<>();

    public Learner() {
        super();
        this.enrolledCourseIDs = new HashSet<>();
        this.finishedCourseIDs = new HashSet<>();
    }

    // For now I have added this. If database needs this kind of method to load all users when site starts.
    // May be removed if not needed at final stage
    public Learner(String username, String password, String email, Set<Long> enrolledCourseIDs,
            Set<Long> finishedCourseIDs) {
        super(username, password, email);
        this.enrolledCourseIDs = enrolledCourseIDs != null ? enrolledCourseIDs : new HashSet<>();
        this.finishedCourseIDs = finishedCourseIDs != null ? finishedCourseIDs : new HashSet<>();
    }

    public Learner(String username, String password, String email) {
        super(username, password, email);
        this.enrolledCourseIDs = new HashSet<>();
        this.finishedCourseIDs = new HashSet<>();
    }
    
    //Getters
    public Set<Long> getEnrolledCourseIDs() {
        return enrolledCourseIDs;
    }

    public Set<Long> getFinishedCourseIDs() {
        return finishedCourseIDs;
    }

    //Setters
    public void setEnrolledCourseIDs(Set<Long> enrolledCourseIDs) {
        this.enrolledCourseIDs = enrolledCourseIDs;
    }

    public void setFinishedCourseIDs(Set<Long> finishedCourseIDs) {
        this.finishedCourseIDs = finishedCourseIDs;
    }

    //Overriden methods
    public void enroll(Course course) {
        Long courseID = course.getId();
        enrolledCourseIDs.add(courseID);
    }

    @Override
    public String getDashboardURL() {
        return "/learner/dashboard";
    }

    @Override
    public String getRole() {
        return "Learner";
    }
}
