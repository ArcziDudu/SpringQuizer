package com.quizer.business.services.Fixtures;

import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuizQuestion;

import java.util.Collections;

public class QuestionFixtures {
    public static ExternalApiResults question1(){
        QuizQuestion build = QuizQuestion.builder()
                .correct_answer("False")
                .category("Entertainment: Books")
                .type("boolean")
                .difficulty("easy")
                .question("The book 1984 was published in 1949.")
                .incorrect_answers(Collections.singletonList("False"))
                .build();
      return  ExternalApiResults.builder()
                .results(Collections.singletonList(build))
                .build();
    }
}
