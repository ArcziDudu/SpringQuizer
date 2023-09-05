package com.quizer.dao;

import com.quizer.model.QuizQuestion;

public interface OpenTdbDao {
    QuizQuestion fetchQuestions();
}
