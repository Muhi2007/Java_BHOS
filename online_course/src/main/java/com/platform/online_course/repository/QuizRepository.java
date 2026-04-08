package com.platform.online_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.online_course.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>{
    
}
