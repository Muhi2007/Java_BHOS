package com.platform.online_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.platform.online_course.model.QuizAttempt;
import com.platform.online_course.model.Learner;
import com.platform.online_course.model.Quiz;


public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    public QuizAttempt findByStudentAndQuiz(Learner student, Quiz quiz);
}
