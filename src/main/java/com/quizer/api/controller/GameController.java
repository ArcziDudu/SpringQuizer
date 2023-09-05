package com.quizer.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class GameController {
    private final String CATEGORIES = "/categories";
    @GetMapping(CATEGORIES)
    public String showQuizGame(){
        return "quizCategories";
    }
}
