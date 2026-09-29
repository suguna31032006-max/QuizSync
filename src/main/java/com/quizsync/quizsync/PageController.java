package com.quizsync.quizsync;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/create-quiz")
    public String createQuiz() {
        return "create-quiz";
    }

    @GetMapping("/join-quiz")
    public String joinQuiz() {
        return "join-quiz";
    }

    @GetMapping("/quiz")
    public String quizPage() {
        return "quiz";
    }
}
