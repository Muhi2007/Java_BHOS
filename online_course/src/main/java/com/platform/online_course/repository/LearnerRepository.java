package com.platform.online_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.platform.online_course.model.Learner;


@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Learner findByEmail(String email);
}
