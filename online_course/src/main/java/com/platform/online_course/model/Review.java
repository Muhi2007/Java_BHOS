package com.platform.online_course.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner reviewer;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    
    private double score;
    private String comment;

    public Review() {
    }

    public Review(Long id, Learner reviewer, Course course, double score, String comment) {
        this.id = id;
        this.reviewer = reviewer;
        this.course = course;
        this.score = score;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Learner getReviewer() {
        return reviewer;
    }

    public void setReviewer(Learner reviewer) {
        this.reviewer = reviewer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
