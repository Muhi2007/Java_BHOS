package com.platform.online_course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.platform.online_course.model.Course;
import com.platform.online_course.model.Teacher;
import com.platform.online_course.repository.CourseRepository;
import com.platform.online_course.repository.TeacherRepository;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TeacherController {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public TeacherController(CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("teacher/create-course")
    public String showCourseCreate(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("CurrUserID");
        String userRole = (String) session.getAttribute("CurrUserRole");

        if (userId == null || userRole == null) {
            return "redirect:/login";
        } else if (!userRole.equals("Teacher")) {
            return "redirect:/learner/dashboard";
        }

        model.addAttribute("course", new Course());

        return "create-course";
    }

    @PostMapping("teacher/create-course")
    public String processCourseCreate(@ModelAttribute Course course, HttpSession session) {
        
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null || !"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/login";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) {
            return "redirect:/login";
        }

        course.setInstructor(teacher);
        Course saveCourse = courseRepository.save(course);

        teacher.getCreatedCourseIDs().add(saveCourse.getId());

        teacherRepository.save(teacher);

        return "redirect:/teacher/dashboard";
    }
    
}
