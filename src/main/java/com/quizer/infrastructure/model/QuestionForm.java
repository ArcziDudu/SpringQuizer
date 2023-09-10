package com.quizer.infrastructure.model;

import com.quizer.infrastructure.constants.QuizDifficulty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionForm {
    List<QuestionAnswer> answers;
    String selectedDifficulty;
    String selectedCategory;
}
