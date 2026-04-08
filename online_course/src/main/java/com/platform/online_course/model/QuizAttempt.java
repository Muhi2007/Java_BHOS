package com.platform.online_course.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class QuizAttempt implements Completable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "learner_id")
    private Learner student;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ElementCollection
    private Map<Long, String> submittion = new HashMap<>();

    private double score;
    private boolean completed = false;

    // interface methods
    @Override
    public boolean isComplete() {
        return this.completed;
    }

    @Override
    public void markAsComplete() {
        this.completed = true;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Learner getStudent() {
        return student;
    }
    public void setStudent(Learner student) {
        this.student = student;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    public Map<Long, String> getSubmittion() {
        return submittion;
    }
    public void setSubmittion(Map<Long, String> submittion) {
        this.submittion = submittion;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
}
