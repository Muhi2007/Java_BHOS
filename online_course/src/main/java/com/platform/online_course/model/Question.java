package com.platform.online_course.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String text;

    @ElementCollection
    protected List<String> options;
    
    protected String correctAnswer;
    //------------------------------------------------------------------------//

    public Question() {}

    // Getters and Setters
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Question other = (Question) obj;
        return this.id != null && this.id.equals(other.id);
    }

    
}
