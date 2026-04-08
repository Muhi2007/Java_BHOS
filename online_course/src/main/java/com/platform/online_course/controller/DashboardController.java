package com.platform.online_course.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.platform.online_course.model.Learner;
import com.platform.online_course.model.Teacher;
import com.platform.online_course.model.Course;
import com.platform.online_course.repository.CourseRepository;
import com.platform.online_course.repository.LearnerRepository;
import com.platform.online_course.repository.QuizAttemptRepository;
import com.platform.online_course.repository.TeacherRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DashboardController {

    private final CourseRepository courseRepository;
    private final LearnerRepository learnerRepository;
    private final QuizAttemptRepository attemptRepository;
    private final TeacherRepository teacherRepository;

    public DashboardController(CourseRepository courseRepository, LearnerRepository learnerRepository, QuizAttemptRepository attemptRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.learnerRepository = learnerRepository;
        this.attemptRepository = attemptRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/learner/dashboard")
    public String showLearnerDashboard(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        }

        String userRole = (String) session.getAttribute("CurrUserRole");

        if (userRole == null) {
            return "redirect:/login";
        } else if (!"Learner".equals(userRole)) {
            return "redirect:/teacher/dashboard";
        }

        Learner user = learnerRepository.findById(userId).orElse(null);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("enrolled", user.getEnrolledCourseIDs());
        model.addAttribute("username", user.getUsername());

        return "learner-dashboard";
    }

    @GetMapping("/learner/my-courses")
    public String showMyCourses(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        }

        Learner user = learnerRepository.findById(userId).orElse(null);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        List<Course> enrolledCourses = courseRepository.findAll().stream()
            .filter(c -> user.getEnrolledCourseIDs().contains(c.getId()))
            .collect(Collectors.toList());

        model.addAttribute("courses", enrolledCourses);
        model.addAttribute("enrolled", user.getEnrolledCourseIDs());
        model.addAttribute("username", user.getUsername());

        return "my-courses";
    }

    @PostMapping("/enroll/{courseID}")
    public String handleEnroll(@PathVariable Long courseID, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        }

        Learner user = learnerRepository.findById(userId).orElse(null);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        Course course = courseRepository.findById(courseID).orElse(null);
        if (course == null) {
            return "redirect:/learner/dashboard";
        }

        course.enroll(user);
        
        courseRepository.save(course);
        learnerRepository.save(user);
        
        return "redirect:/learner/dashboard";
    }
    
    @GetMapping("/course/{id}")
    public String showCourseDetail(@PathVariable Long id, Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        }

        Learner user = learnerRepository.findById(userId).orElse(null);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return "redirect:/learner/dashboard";
        }

        // Build a map of quizId -> attempt for this learner to show completion state
        model.addAttribute("course", course);
        model.addAttribute("enrolled", user.getEnrolledCourseIDs());
        model.addAttribute("completedAttempts", 
            attemptRepository.findAll().stream()
                .filter(a -> a.getStudent() != null && a.getStudent().getId().equals(userId)
                          && a.isComplete())
                .map(a -> a.getQuiz().getId())
                .collect(java.util.stream.Collectors.toSet()));
        
        return "course-detail";
    }
    
    @GetMapping("teacher/dashboard")
    public String showTeacherDashboard(Model model, HttpSession session) {

        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        }

        String userRole = (String) session.getAttribute("CurrUserRole");

        if (userRole == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(userRole)) {
            return "redirect:/learner/dashboard";
        }

        Teacher user = teacherRepository.findById(userId).orElse(null);

        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        List<Course> teacherCourses = courseRepository.findAll().stream()
            .filter(c -> c.getInstructor() != null
                && c.getInstructor().getId() != null
                && c.getInstructor().getId().equals(userId))
            .collect(Collectors.toList());

        int totalCoursesCreated = teacherCourses.size();
        int totalLearners = teacherCourses.stream()
            .mapToInt(c -> c.getEnrolledLearners() != null ? c.getEnrolledLearners().size() : 0)
            .sum();

        model.addAttribute("courses", teacherCourses);
        model.addAttribute("teacherCourses", teacherCourses);
        model.addAttribute("createdCourseIds", user.getCreatedCourseIDs());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("totalCoursesCreated", totalCoursesCreated);
        model.addAttribute("totalLearners", totalLearners);
        model.addAttribute("publishedCount", 0);
        model.addAttribute("pendingReviewsCount", 0);

        return "teacher-dashboard";
    }
}