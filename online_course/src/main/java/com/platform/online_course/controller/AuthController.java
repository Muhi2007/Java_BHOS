package com.platform.online_course.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.platform.online_course.model.Learner;
import com.platform.online_course.repository.LearnerRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;





@Controller
public class AuthController{
    // Injection
    private final LearnerRepository learnerRepository;

    public AuthController(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    // Basically, whenever user goes to the given address 
    //empty Learner class generates to be filled up by user
    @GetMapping("/signup")
    public String showSignupForm(Model model, HttpSession session) {
        if (session.getAttribute("CurrUserID") != null) return "redirect:/learner/dashboard";

        model.addAttribute("learner", new Learner());

        return "signup";
    }
    
    @PostMapping("/signup")
    public String processSignup(@ModelAttribute Learner learner) { // The atribiute automatically creates Learner by itself
        Learner savedLearner = learnerRepository.findByEmail(learner.getEmail());

        if (savedLearner == null) learnerRepository.save(learner);
        else return "redirect:/signup?error";

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("CurrUserID") != null) {
            return "redirect:/learner/dashboard";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Learner foundLearner = learnerRepository.findByEmail(email.toLowerCase());

        if (foundLearner != null && Objects.equals(foundLearner.getPassword(), password)) {
            session.setAttribute("CurrUserID", foundLearner.getId());

            return "redirect:/learner/dashboard";
        }

        return "redirect:/login?error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }
}
