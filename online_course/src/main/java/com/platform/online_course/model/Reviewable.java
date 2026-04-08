package com.platform.online_course.model;

public interface Reviewable {
    void addRating(Review review);

    double getAverage();
}
