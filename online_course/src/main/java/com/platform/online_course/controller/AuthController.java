package com.platform.online_course.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.platform.online_course.model.BaseUser;
import com.platform.online_course.model.Learner;
import com.platform.online_course.model.Teacher;
import com.platform.online_course.repository.LearnerRepository;
import com.platform.online_course.repository.TeacherRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
public class AuthController{
    // Injection
    private final LearnerRepository learnerRepository;
    private final TeacherRepository teacherRepository;

    public AuthController(LearnerRepository learnerRepository, TeacherRepository teacherRepository) {
        this.learnerRepository = learnerRepository;
        this.teacherRepository = teacherRepository;
    }

    // Basically, whenever user goes to the given address 
    //empty Learner class generates to be filled up by user
    @GetMapping("/signup")
    public String showSignupForm(HttpSession session) {
        if (session.getAttribute("CurrUserID") != null){
            if (session.getAttribute("CurrUserRole").equals("Teacher")) {
                return "redirect:/teacher/dashboard";
            } else {
                return "redirect:/learner/dashboard";
            }
        }

        return "signup";
    }
    
    @PostMapping("/signup")
    public String processSignup(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam boolean isLearner) {
           
        try {
            if (isLearner) {
                Learner newLearner = new Learner(username, password, email);

                learnerRepository.save(newLearner);
            } else {
                Teacher newTeacher = new Teacher(username, password, email);
                newTeacher.setDepartment("Pending");

                teacherRepository.save(newTeacher);
            }

            return "redirect:/login?registered=true";
        } catch (Exception e) {
            return "redirect:/signup?error";
        }
       
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("CurrUserID") != null) {
            return "redirect:/learner/dashboard";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, @RequestParam boolean isLearner,HttpSession session) {
        BaseUser foundUser = isLearner? learnerRepository.findByEmail(email.toLowerCase()) : teacherRepository.findByEmail(email.toLowerCase());

        if (foundUser != null && Objects.equals(foundUser.getPassword(), password)) {
            session.setAttribute("CurrUserID", foundUser.getId());
            session.setAttribute("CurrUserRole", foundUser.getRole());

            return "redirect:" + foundUser.getDashboardURL();
        }

        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }
}
