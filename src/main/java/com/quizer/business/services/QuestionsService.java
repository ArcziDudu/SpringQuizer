package com.quizer.business.services;

import com.quizer.business.services.dao.QuestionDao;
import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuestionAnswer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class QuestionsService {

    private final QuestionDao questionDao;

    public Flux<ExternalApiResults> fetchQuestions(String category, String difficulty) {
        return questionDao.getResultsFromExternalApi(chosenAmount(difficulty),
                chosenCategory(category),
                chosenType(difficulty),
                difficulty);
    }
    public int amountOfPoints(List<QuestionAnswer> userAnswers){
        int amountOfPoints = 0;
        for (QuestionAnswer userAnswer : userAnswers) {
            if(userAnswer.getYourAnswer().equalsIgnoreCase((userAnswer.getCorrectAnswer()))){
                amountOfPoints++;
            }
        }
        return amountOfPoints;
    }
    private String chosenType(String difficulty) {

        String type;
        switch (difficulty) {
            case "medium", "hard" -> type = "multiple";
            default -> type = "boolean";
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

    private int chosenCategory(String category) {
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
        }
        return categoryType;
    }
}
