package com.quizer.business.services.dao;

import com.quizer.infrastructure.model.ExternalApiResults;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final WebClient webClient;
    private final String QUESTIONS = "api.php";


    @Override
    public Flux<ExternalApiResults> getResultsFromExternalApi(int amount, int category, String type, String difficulty) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(QUESTIONS)
                        .queryParam("amount", amount)
                        .queryParam("category", category)
                        .queryParam("type", type)
                        .queryParam("difficulty", difficulty)
                        .build())
                .retrieve()
                .bodyToFlux(ExternalApiResults.class);
    }
}
