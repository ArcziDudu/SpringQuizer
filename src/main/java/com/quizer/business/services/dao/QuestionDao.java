package com.quizer.business.services.dao;


import com.quizer.infrastructure.model.ExternalApiResults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QuestionDao {
    Flux<ExternalApiResults> getResultsFromExternalApi(int amount, int category, String type, String difficulty);
}
