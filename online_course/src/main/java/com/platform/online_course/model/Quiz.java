package com.platform.online_course.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Quiz extends Assessment {
    private int quizNumber;
    private String difficulty;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_id")
    private List<Question> questions = new ArrayList<>();

    @Transient
    private Map<Long, String> submittedAnswers = new HashMap<>();

    public Quiz() {
    }

    public void setQuizNumber(int quizNumber) {
        this.quizNumber = quizNumber;
    }

    public int getQuizNumber() {
        return this.quizNumber;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions != null ? questions : new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void submitAnswer(Long questionIndex, String answer) throws IllegalArgumentException{
        if (questionIndex < 0) {
            throw new IllegalArgumentException("Question index cannot be negative");
        }
        submittedAnswers.put(questionIndex, answer);
    }

    public void submitAnswers(Map<Long, String> answers) {
        if (answers == null || answers.isEmpty()) {
            return;
        }
        submittedAnswers.putAll(answers);
    }

    public void clearSubmittedAnswers() {
        submittedAnswers.clear();
    }

    @Override
    public double calculateScore(Map<Long, String> answerMap) {
        if (this.questions == null || this.questions.isEmpty()) return 0;

        int correctCount = 0;
        
        for (Question q : this.questions) {
            String correctAns = normalize(q.getCorrectAnswer());
            String submmitAns = answerMap.get(q.getId());

            if (submmitAns != null && correctAns.equalsIgnoreCase(submmitAns)) correctCount ++;
        }

        return ((double)correctCount / this.questions.size()) * this.getMaxScore();

    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.trim().toLowerCase();
    }
}
