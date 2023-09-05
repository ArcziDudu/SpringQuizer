package com.quizer.externalApiClient;

import com.quizer.dao.OpenTdbDao;
import com.quizer.model.QuizQuestion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
@AllArgsConstructor
public class openTdbClientImpl implements OpenTdbDao {
    private final WebClient webClient;
    @Override
    public QuizQuestion fetchQuestions() {
        String QUESTIONS = "api.php";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(QUESTIONS)
                        .queryParam("amount", 30)
                        .build())
                .retrieve()
                .bodyToMono(QuizQuestion.class)
                .block();
    }
}
