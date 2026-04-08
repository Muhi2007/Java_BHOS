package com.platform.online_course.repository;

import com.platform.online_course.model.Course;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	Optional<Course> findByAssessments_Id(Long assessmentId);
}