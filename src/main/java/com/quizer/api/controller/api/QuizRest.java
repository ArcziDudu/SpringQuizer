package com.quizer.api.controller.api;


import com.quizer.business.services.QuestionsService;
import com.quizer.infrastructure.model.ExternalApiResults;
import com.quizer.infrastructure.model.QuizQuestion;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(QuizRest.API)
@AllArgsConstructor
public class QuizRest {
    public static final String API = "/api";
    public static final String API_CATEGORY_LEVEL = "/{category}/{selectedDifficulty}";
    private final QuestionsService questionsService;

    @GetMapping(value = API_CATEGORY_LEVEL)
    public ResponseEntity<List<QuizQuestion>> fetchQuestionsByCategoryAndLevel(
            @PathVariable String category,
            @PathVariable String selectedDifficulty){
        int categoryTranslateToIntForExternalApi = questionsService.chosenCategory(category);
        Flux<ExternalApiResults> externalApiResultsFlux = questionsService
                .fetchQuestions(categoryTranslateToIntForExternalApi, selectedDifficulty);
        List<QuizQuestion> results = Objects.requireNonNull(externalApiResultsFlux.blockFirst()).results();
        return ResponseEntity.ok(results);
    }
}
