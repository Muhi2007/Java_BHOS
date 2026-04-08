package com.platform.online_course.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.platform.online_course.model.Learner;
import com.platform.online_course.model.Question;
import com.platform.online_course.model.Quiz;
import com.platform.online_course.model.QuizAttempt;
import com.platform.online_course.repository.CourseRepository;
import com.platform.online_course.repository.LearnerRepository;
import com.platform.online_course.repository.QuizAttemptRepository;
import com.platform.online_course.repository.QuizRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CourseController {
    private CourseRepository courseRepository;
    private LearnerRepository learnerRepository;
    private QuizRepository quizRepository;
    private QuizAttemptRepository attemptRepository;

    CourseController(CourseRepository courseRepository, LearnerRepository learnerRepository, QuizRepository quizRepository, QuizAttemptRepository attemptRepository) {
        this.courseRepository = courseRepository;
        this.learnerRepository = learnerRepository;
        this.quizRepository = quizRepository;
        this.attemptRepository = attemptRepository;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("platformName", "Our Awesome Course Platform");
        
        return "index"; 
    }

    @GetMapping("/quiz/{quizId}/start")
    public String startQuiz(@PathVariable Long quizId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("CurrUserID");
        
        if (userId == null) {
            return "redirect:/login";
        }

        Learner student = learnerRepository.findById(userId).orElse(null);
        if (student == null) {
            return "redirect:/login";
        }

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            return "redirect:/learner/dashboard";
        }

        QuizAttempt attempt = attemptRepository.findByStudentAndQuiz(student, quiz);
        
        if (attempt == null || attempt.isComplete()) {
            attempt = new QuizAttempt();
            attempt.setStudent(student);
            attempt.setQuiz(quiz);
            attemptRepository.save(attempt);
        } else {
            attemptRepository.delete(attempt);
            attempt = new QuizAttempt();
            attempt.setStudent(student);
            attempt.setQuiz(quiz);
            attemptRepository.save(attempt);
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("attemptID", attempt.getId());

        return "take-quiz";
    }

    @PostMapping("/quiz/{attemptId}/submit")
    public String submitQuiz(@PathVariable Long attemptId, @RequestParam Map<String, String> answersMap) {

        QuizAttempt attempt = attemptRepository.findById(attemptId).orElse(null);

        if (attempt == null || attempt.getQuiz() == null) {
            return "redirect:/learner/dashboard";
        }

        Quiz quiz = attempt.getQuiz();
        Map<Long, String> normalizedAnswers = new HashMap<>();
        
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            Question question = quiz.getQuestions().get(i);
            if (question == null || question.getId() == null) {
                continue;
            }

            Long questionId = question.getId();
            String answer = null;

            System.out.println(answersMap.toString()); // For testing puposes

            // Accept multiple common form naming styles
            answer = answersMap.get(String.valueOf(questionId));
            if (answer == null) {
                answer = answersMap.get("question_" + questionId);
            }
            if (answer == null) {
                answer = answersMap.get(String.valueOf(i));
            }
            if (answer == null) {
                answer = answersMap.get("question_" + i);
            }

            if (answer != null) {
                // FIX 2: Use the exact Question ID as the key!
                normalizedAnswers.put(questionId, answer);
            }
        }

        // FIX 1: Pass the answers directly to the grading algorithm (Stateless!)
        double score = quiz.calculateScore(normalizedAnswers);

        // Save the data to the Attempt (The Receipt), NOT the Quiz!
        attempt.setSubmittion(normalizedAnswers); // Ensure your attempt class has this setter
        attempt.setScore(score);
        attempt.markAsComplete();
        attemptRepository.save(attempt);

        return "redirect:/quiz/" + attemptId + "/result";
    }

    @GetMapping("/quiz/{attemptId}/result")
    public String showResult(@PathVariable Long attemptId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("CurrUserID");
        if (userId == null) {
            return "redirect:/login";
        }

        QuizAttempt attempt = attemptRepository.findById(attemptId).orElse(null);
        if (attempt == null || attempt.getQuiz() == null) {
            return "redirect:/learner/dashboard";
        }

        Quiz quiz = attempt.getQuiz();
        int totalQuestions = quiz.getQuestions().size();
        int maxScore = quiz.getMaxScore();

        int correctCount = 0;
        if (maxScore > 0 && totalQuestions > 0) {
            correctCount = (int) Math.round((attempt.getScore() / maxScore) * totalQuestions);
        }

        model.addAttribute("attempt", attempt);
        model.addAttribute("quiz", quiz);
        model.addAttribute("quizId", quiz.getId());
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("passed", attempt.getScore() >= (maxScore * 0.5));

        // Resolve owning course from Course.assessments relation using this quiz id.
        courseRepository.findByAssessments_Id(quiz.getId())
            .map(course -> course.getId())
            .ifPresent(courseId -> model.addAttribute("courseId", courseId));

        return "quiz-result";
    }
}
