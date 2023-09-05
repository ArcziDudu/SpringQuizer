package com.quizer.model;

import java.util.List;

public record QuizQuestion(String category,
                           String type,
                           String difficulty,
                           String question,
                           String correct_answer,
                           List<String> incorrect_answers) {
}
