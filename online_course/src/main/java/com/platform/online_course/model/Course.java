package com.platform.online_course.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Course implements Enrollable, Reviewable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;
    protected String category;
    protected String description;
    protected double averageDuration;
    protected double rating;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    protected Teacher instructor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    protected List<Assessment> assessments = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "course_enrollment",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "learner_id")
    )
    protected Set<Learner> enrolledLearners = new HashSet<>();

    public Course() {}
    
    public Course(CourseBuilder builder) {
        this.name = builder.name;
        this.category = builder.category;
        this.description = builder.description;
        this.averageDuration = builder.averageDuration;
        this.instructor = builder.instructor;
        this.assessments = builder.assessments != null ? builder.assessments : new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.enrolledLearners = new HashSet<>();
        this.rating = 0;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public double getRating() {
        return rating;
    }

    public Teacher getInstructor() {
        return instructor;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Set<Learner> getEnrolledLearners() {
        return enrolledLearners;
    }


    // Abstract methods
    @Override
    public void enroll(Learner learner) {
        if (learner == null) {
            return;
        }
        this.enrolledLearners.add(learner);
        if (this.id != null) {
            learner.getEnrolledCourseIDs().add(this.id);
        }
    }

    @Override
    public void drop(Learner learner) {
        if (learner == null) {
            return;
        }
        this.enrolledLearners.remove(learner);
        if (this.id != null) {
            learner.getEnrolledCourseIDs().remove(this.id);
        }
    }

    @Override
    public void addRating(Review review) {
        if (review == null) {
            return;
        }
        review.setCourse(this);
        this.reviews.add(review);
        this.rating = getAverage();
    }

    @Override
    public double getAverage() {
        if (this.reviews == null || this.reviews.isEmpty()) {
            return 0d;
        }
        double sumrev = 0d;
        for (Review rev: this.reviews) {
            sumrev += rev.getScore();
        }
        return sumrev/this.reviews.size();
    }

    // Builder Design Pattern
    public static class CourseBuilder {
        private String name;
        private String category;
        private String description;
        private double averageDuration;
        private Teacher instructor;
        private List<Assessment> assessments = new ArrayList<>();

        public CourseBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public CourseBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public CourseBuilder setAverageDuration(double averageDuration) {
            this.averageDuration = averageDuration;
            return this;
        }

        public CourseBuilder setInstructor(Teacher instructor) {
            this.instructor = instructor;
            return this;
        }

        public CourseBuilder setAssessments(List<Assessment> assessments) {
            if (assessments != null) {
                this.assessments = assessments;
            }
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
