package com.platform.online_course.model;

public interface Enrollable {
    void enroll(Learner learner);

    void drop(Learner learner);
}
