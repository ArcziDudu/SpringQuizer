package com.quizer.business.services;

import com.quizer.business.services.dao.QuestionDao;
import com.quizer.domain.exception.InvalidCategoriesException;
import com.quizer.domain.exception.InvalidDifficultyException;
import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuestionAnswer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@AllArgsConstructor
public class QuestionsService {

    private final QuestionDao questionDao;

    public Flux<ExternalApiResults> fetchQuestions(int category, String difficulty) {
        return questionDao.getResultsFromExternalApi(chosenAmount(difficulty),
                category,
                chosenType(difficulty),
                difficulty);
    }

    public int amountOfPoints(List<QuestionAnswer> userAnswers) {
        int amountOfPoints = 0;
        for (QuestionAnswer userAnswer : userAnswers) {
            if (userAnswer.getYourAnswer().equalsIgnoreCase((userAnswer.getCorrectAnswer()))) {
                amountOfPoints++;
            }
        }
        return amountOfPoints;
    }

    private String chosenType(String difficulty) {
        String type;
        switch (difficulty) {
            case "medium", "hard" -> type = "multiple";
            case "easy" -> type = "boolean";
            default -> {
                throw new InvalidDifficultyException("Invalid difficulty: " + difficulty
                        +". You can choose from three options: easy, medium or hard");
            }
        }
        return type;
    }

    private int chosenAmount(String difficulty) {
        int amount = 0;
        switch (difficulty) {
            case "easy" -> amount = 5;
            case "medium" -> amount = 10;
            case "hard" -> amount = 15;
        }
        return amount;
    }

    public int chosenCategory(String category) {
        int categoryType = 0;
        int[] possibleValues = {10, 11, 12, 14, 18};
        Random random = new Random();
        switch (category) {
            case "books" -> categoryType = 10;
            case "films" -> categoryType = 11;
            case "music" -> categoryType = 12;
            case "television" -> categoryType = 14;
            case "computers" -> categoryType = 18;
            case "random" -> categoryType = possibleValues[random.nextInt(possibleValues.length)];
            default -> {
                throw new InvalidCategoriesException("Invalid category: " + category
                        +". You can choose from five options: random, books, films, music, television, computers");
            }
        }
        return categoryType;
    }

    public String CategoryFromIntToString(int category) {
        String categoryType = "random";
        switch (category) {
            case 10 -> categoryType = "books";
            case 11 -> categoryType = "films";
            case 12 -> categoryType = "music";
            case 14 -> categoryType = "television";
            case 18 -> categoryType = "computers";
        }
        return categoryType;
    }
}
