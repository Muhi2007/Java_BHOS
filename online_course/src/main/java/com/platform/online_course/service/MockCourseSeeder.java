package com.platform.online_course.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.platform.online_course.model.Course;
import com.platform.online_course.model.Question;
import com.platform.online_course.model.Quiz;
import com.platform.online_course.repository.CourseRepository;

import jakarta.annotation.PostConstruct;

@Component
public class MockCourseSeeder {

    private final CourseRepository courseRepository;

    public MockCourseSeeder(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostConstruct
    public void seedCourses() {
        if (courseRepository.count() > 0) {
            return;
        }

        // ── Course 1: Java Fundamentals ──────────────────────────────────────
        Course javaBasics = new Course.CourseBuilder()
                .setName("Java Fundamentals")
                .setCategory("Programming")
                .setDescription("Master core Java syntax, object-oriented programming, collections, and exception handling through hands-on exercises.")
                .setAverageDuration(18)
                .build();

        Quiz javaQuiz1 = buildQuiz("OOP Basics", "Easy", 10, 1,
            Arrays.asList(
                buildQuestion("What keyword is used to create an object in Java?",
                    Arrays.asList("new", "create", "make", "build"), "new"),
                buildQuestion("Which of these is NOT a valid access modifier in Java?",
                    Arrays.asList("public", "private", "protected", "global"), "global"),
                buildQuestion("What does OOP stand for?",
                    Arrays.asList("Object-Oriented Programming", "Online Object Processing", "Output Object Programming", "Object Order Protocol"), "Object-Oriented Programming")
            )
        );

        Quiz javaQuiz2 = buildQuiz("Collections & Generics", "Medium", 10, 2,
            Arrays.asList(
                buildQuestion("Which interface does ArrayList implement?",
                    Arrays.asList("List", "Set", "Map", "Queue"), "List"),
                buildQuestion("What is the time complexity of ArrayList.get(index)?",
                    Arrays.asList("O(1)", "O(n)", "O(log n)", "O(n²)"), "O(1)"),
                buildQuestion("Which collection does NOT allow duplicate elements?",
                    Arrays.asList("HashSet", "ArrayList", "LinkedList", "Vector"), "HashSet")
            )
        );

        javaBasics.getAssessments().add(javaQuiz1);
        javaBasics.getAssessments().add(javaQuiz2);

        // ── Course 2: Spring Boot Essentials ────────────────────────────────
        Course springBoot = new Course.CourseBuilder()
                .setName("Spring Boot Essentials")
                .setCategory("Backend Development")
                .setDescription("Build production-ready RESTful and MVC applications with Spring Boot, covering auto-configuration, JPA, and testing.")
                .setAverageDuration(24)
                .build();

        Quiz springQuiz1 = buildQuiz("Spring Core Concepts", "Easy", 10, 1,
            Arrays.asList(
                buildQuestion("Which annotation marks a class as a Spring-managed component?",
                    Arrays.asList("@Component", "@Bean", "@Entity", "@Service", "@Test"), "@Component"),
                buildQuestion("What does @Autowired do?",
                    Arrays.asList("Injects dependencies automatically", "Creates a new bean", "Maps HTTP routes", "Validates input"), "Injects dependencies automatically"),
                buildQuestion("Which annotation is used to define a REST controller?",
                    Arrays.asList("@RestController", "@Controller", "@Endpoint", "@API"), "@RestController")
            )
        );

        Quiz springQuiz2 = buildQuiz("Spring Data & JPA", "Medium", 10, 2,
            Arrays.asList(
                buildQuestion("Which annotation maps a class to a database table?",
                    Arrays.asList("@Entity", "@Table", "@Column", "@Row"), "@Entity"),
                buildQuestion("What does CrudRepository.save() do when an entity has no ID?",
                    Arrays.asList("Inserts a new record", "Updates existing record", "Throws an error", "Deletes the record"), "Inserts a new record"),
                buildQuestion("Which file typically configures database settings in Spring Boot?",
                    Arrays.asList("application.properties", "config.xml", "database.yml", "settings.json"), "application.properties")
            )
        );

        springBoot.getAssessments().add(springQuiz1);
        springBoot.getAssessments().add(springQuiz2);

        // ── Course 3: SQL for Beginners ──────────────────────────────────────
        Course sqlIntro = new Course.CourseBuilder()
                .setName("SQL for Beginners")
                .setCategory("Databases")
                .setDescription("Learn query design, joins, aggregation, subqueries, and indexing fundamentals to work confidently with relational databases.")
                .setAverageDuration(15)
                .build();

        Quiz sqlQuiz1 = buildQuiz("SQL Queries Fundamentals", "Easy", 10, 1,
            Arrays.asList(
                buildQuestion("Which SQL keyword retrieves data from a table?",
                    Arrays.asList("SELECT", "GET", "FETCH", "READ"), "SELECT"),
                buildQuestion("Which clause filters rows in a SELECT statement?",
                    Arrays.asList("WHERE", "HAVING", "FILTER", "LIMIT"), "WHERE"),
                buildQuestion("What does the ORDER BY clause do?",
                    Arrays.asList("Sorts the result set", "Filters rows", "Groups rows", "Joins tables"), "Sorts the result set")
            )
        );

        Quiz sqlQuiz2 = buildQuiz("Joins & Aggregation", "Medium", 10, 2,
            Arrays.asList(
                buildQuestion("Which JOIN returns all rows from both tables, with nulls where no match exists?",
                    Arrays.asList("FULL OUTER JOIN", "INNER JOIN", "LEFT JOIN", "CROSS JOIN"), "FULL OUTER JOIN"),
                buildQuestion("Which function counts the number of rows?",
                    Arrays.asList("COUNT()", "SUM()", "TOTAL()", "NUM()"), "COUNT()"),
                buildQuestion("Which clause filters groups after GROUP BY?",
                    Arrays.asList("HAVING", "WHERE", "FILTER", "WHEN"), "HAVING")
            )
        );

        sqlIntro.getAssessments().add(sqlQuiz1);
        sqlIntro.getAssessments().add(sqlQuiz2);

        courseRepository.saveAll(Arrays.asList(javaBasics, springBoot, sqlIntro));
    }

    // --------------------------------- Helper methods --------------------------------------

    private Quiz buildQuiz(String name, String difficulty, int maxScore, int quizNumber, List<Question> questions) {
        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setDifficulty(difficulty);
        quiz.setMaxScore(maxScore);
        quiz.setQuizNumber(quizNumber);
        quiz.setQuestions(questions);
        return quiz;
    }

    private Question buildQuestion(String text, List<String> options, String correctAnswer) {
        Question q = new Question();
        q.setText(text);
        q.setOptions(new ArrayList<>(options));
        q.setCorrectAnswer(correctAnswer);
        return q;
    }
}
