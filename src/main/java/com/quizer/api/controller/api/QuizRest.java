package com.quizer.api.controller.api;


import com.quizer.business.services.QuestionsService;
import com.quizer.infrastructure.model.ExternalApiResults;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(QuizRest.API)
@AllArgsConstructor
public class QuizRest {
    public static final String API = "/api";
    public static final String API_das = "/{category}/{level}";
    private final QuestionsService questionsService;

    @GetMapping(value = API_das)
    public ResponseEntity<Flux<ExternalApiResults>> getRepositories(
            @PathVariable String category,
            @PathVariable String level) {
        return ResponseEntity
                .ok(questionsService.fetchQuestions(category, level));
    }
}
