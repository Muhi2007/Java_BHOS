package com.platform.online_course.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.platform.online_course.model.Assessment;
import com.platform.online_course.model.Course;
import com.platform.online_course.model.Question;
import com.platform.online_course.model.Quiz;
import com.platform.online_course.model.Teacher;
import com.platform.online_course.repository.CourseRepository;
import com.platform.online_course.repository.QuestionRepository;
import com.platform.online_course.repository.QuizRepository;
import com.platform.online_course.repository.TeacherRepository;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class TeacherController {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public TeacherController(CourseRepository courseRepository, TeacherRepository teacherRepository, QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
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
    

    @GetMapping("course/{courseId}/create-quiz")
    public String showQuizCreate(@PathVariable Long courseId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        model.addAttribute("quiz", new Quiz());
        model.addAttribute("courseId", courseId);

        return "quiz-create";
    }

    @PostMapping("course/{courseId}/create-quiz")
    public String processQuizCreate(@PathVariable Long courseId, @ModelAttribute Quiz quiz, HttpSession session) {

        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Course currCourse = courseRepository.findById(courseId).orElse(null);
        if (currCourse == null) return "redirect:/teacher/dashboard";

        int numOfQuiz = currCourse.getAssessments().size(); //For auto setting the index of each quiz
        quiz.setQuizNumber(numOfQuiz+1);

        Quiz savedQuiz = quizRepository.save(quiz);

        currCourse.getAssessments().add(savedQuiz);
        courseRepository.save(currCourse);

        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/course/{courseId}/quizzes")
    public String showCourseQuizes(@PathVariable Long courseId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Course currCourse = courseRepository.findById(courseId).orElse(null);
        if (currCourse == null) return "redirect:/teacher/dashboard";

        List<Assessment> quizList = currCourse.getAssessments();

        model.addAttribute("quizzes", quizList);
        model.addAttribute("courseId", courseId);

        return "course-quizzes";
    }
    
    @GetMapping("/course/{courseId}/quiz/{quizId}/edit")
    public String showQuizEditForm(@PathVariable Long courseId, @PathVariable Long quizId, HttpSession session, Model model){
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Quiz currQuiz = quizRepository.findById(quizId).orElse(null);
        if (currQuiz == null) return "redirect:/course/"+courseId+"/quizzes";

        model.addAttribute("quiz", currQuiz);
        model.addAttribute("courseId", courseId);

        return "quiz-edit";
    }

    @PostMapping("/course/{courseId}/quiz/{quizId}/edit")
    public String processQuizEdit(@PathVariable Long courseId, @PathVariable Long quizId, @ModelAttribute Quiz updatedQuiz, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Quiz currQuiz = quizRepository.findById(quizId).orElse(null);
        if (currQuiz != null) {
            currQuiz.setDescription(updatedQuiz.getDescription());
            currQuiz.setDifficulty(updatedQuiz.getDifficulty());
            currQuiz.setName(updatedQuiz.getName());
            currQuiz.setMaxScore(updatedQuiz.getMaxScore());

            quizRepository.save(currQuiz);
        }

        return "redirect:/course/"+courseId+"/quiz/"+quizId+"/questions";
    }

    @PostMapping("/course/{courseId}/quiz/{quizId}/delete")
    public String deleteQuiz(@PathVariable Long courseId, @PathVariable Long quizId, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();
        
        Course course = courseRepository.findById(courseId).orElse(null);
        Quiz quiz = quizRepository.findById(quizId).orElse(null);

        if (course != null && quiz != null) {
            course.getAssessments().remove(quiz);
            courseRepository.save(course);

            quizRepository.delete(quiz);
        }

        return "redirect:/course/"+courseId+"/quiz/"+quizId+"/questions";
    }

    @GetMapping("/course/{courseId}/quiz/{quizId}/questions")
    public String showQuizQuestions(@PathVariable Long courseId, @PathVariable Long quizId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Course currCourse = courseRepository.findById(courseId).orElse(null);
        if (currCourse == null) return "redirect:/teacher/dashboard";

        Quiz currQuiz = quizRepository.findById(quizId).orElse(null);
        if (currQuiz == null) return "redirect:/course/"+courseId+"/quizzes";

        List<Question> questions = currQuiz.getQuestions();
        
        model.addAttribute("questions", questions);
        model.addAttribute("courseId", courseId);
        model.addAttribute("quizId", quizId);

        return "quiz-questions";
    }

    @GetMapping("/course/{courseId}/quiz/{quizId}/create-question")
    public String showQuizCreate(@PathVariable Long courseId, @PathVariable Long quizId, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Course currCourse = courseRepository.findById(courseId).orElse(null);
        if (currCourse == null) return "redirect:/teacher/dashboard";

        Quiz currQuiz = quizRepository.findById(quizId).orElse(null);
        if (currQuiz == null) return "redirect:/course/"+courseId+"/quizzes";

        model.addAttribute("question", new Question());
        model.addAttribute("quizId", quizId);
        model.addAttribute("courseId", courseId);

        return "question-create";
    }

    @PostMapping("/course/{courseId}/quiz/{quizId}/create-question")
    public String processQuestionCreate(@PathVariable Long courseId, @PathVariable Long quizId, @ModelAttribute Question question, HttpSession session) {

        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Course currCourse = courseRepository.findById(courseId).orElse(null);
        if (currCourse == null) return "redirect:/teacher/dashboard";

        Quiz currQuiz = quizRepository.findById(quizId).orElse(null);
        if (currQuiz == null) return "redirect:/course/"+courseId+"/quizzes";

        Question savedQuestion = questionRepository.save(question);

        currQuiz.getQuestions().add(savedQuestion);
        quizRepository.save(currQuiz);

        return "redirect:/course/"+courseId+"/quiz/"+quizId+"/questions";
    }

    @GetMapping("/course/{courseId}/quiz/{quizId}/question/{questionId}/edit")
    public String showQuestionEditForm(@PathVariable Long courseId, @PathVariable Long quizId, @PathVariable Long questionId, HttpSession session, Model model){
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Course currCourse = courseRepository.findById(courseId).orElse(null);
        if (currCourse == null) return "redirect:/teacher/dashboard";

        Quiz currQuiz = quizRepository.findById(quizId).orElse(null);
        if (currQuiz == null) return "redirect:/course/"+courseId+"/quizzes";

        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) return "redirect:/course/"+courseId+"/quiz/"+quizId+"/questions";

        model.addAttribute("question", question);
        model.addAttribute("courseId", courseId);
        model.addAttribute("quizId", quizId);

        return "question-edit";
    }

    @PostMapping("/course/{courseId}/quiz/{quizId}/question/{questionId}/edit")
    public String processQuizEdit(@PathVariable Long courseId, @PathVariable Long quizId, @PathVariable Long questionId, @ModelAttribute Question updatedQuestion, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return "redirect:/teacher/dashboard";

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) return "redirect:/course/"+courseId+"/quizzes";

        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) return "redirect:/course/"+courseId+"/quiz/"+quizId+"/questions";


        Question currQuestion = questionRepository.findById(questionId).orElse(null);
        if (currQuestion != null) {
            currQuestion.setOptions(updatedQuestion.getOptions());
            currQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
            currQuestion.setText(updatedQuestion.getText());

            questionRepository.save(currQuestion);
        }

        return "redirect:/course/"+courseId+"/quizzes";
    }

    @PostMapping("course/{courseId}/quiz/{quizId}/question/{questionId}/delete")
    public String deleteQuestion(@PathVariable Long courseId, @PathVariable Long quizId, @PathVariable Long questionId, HttpSession session) {
        Long userId = (Long) session.getAttribute("CurrUserID");

        if (userId == null) {
            return "redirect:/login";
        } else if (!"Teacher".equals(session.getAttribute("CurrUserRole"))) {
            return "redirect:/learner/dashboard";
        }

        Teacher teacher = teacherRepository.findById(userId).orElse(null);

        if (teacher == null) return "redirect:/login";
        else if (!teacher.getCreatedCourseIDs().contains(courseId)) return "redirect:" + teacher.getDashboardURL();

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return "redirect:/teacher/dashboard";

        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) return "redirect:/course/"+courseId+"/quizzes";

        Question question = questionRepository.findById(questionId).orElse(null);
        if (question == null) return "redirect:/course/"+courseId+"/quiz/"+quizId+"/questions";

        quiz.getQuestions().remove(question);
        quizRepository.save(quiz);

        questionRepository.delete(question);

        return "redirect:/course/"+courseId+"/quiz/"+quizId+"/questions";
    }
    
}
