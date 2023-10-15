package com.quizer.externalApiClient;

import com.quizer.business.services.QuestionsService;
import com.quizer.externalApiClient.config.WiremockConfig;
import com.quizer.infrastructure.model.ExternalApiResults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

class ExternalApiTest extends WiremockConfig {
    @Autowired
    private QuestionsService questionsService;

    @Test
    void thatReturnCorrectQuizQuestionObject() {
        wireMockServer.stubFor(get(urlPathEqualTo("/api.php"))
                .withQueryParam("amount", equalTo("5"))
                .withQueryParam("category", equalTo("10"))
                .withQueryParam("difficulty", equalTo("easy"))
                .withQueryParam("type", equalTo("boolean"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("wiremock/externalApiResult.json")));

        Flux<ExternalApiResults> externalApiResults = questionsService.fetchQuestions(10, "easy");

        StepVerifier.create(externalApiResults)
                .consumeNextWith(result -> {
                    Assertions.assertEquals(5, result.results().size());
                    boolean allEasy = result.results().stream()
                            .allMatch(quizQuestion -> "easy".equals(quizQuestion.difficulty()));
                    boolean allBooks = result.results().stream()
                            .allMatch(quizQuestion -> "Entertainment: Books".equals(quizQuestion.category()));
                    boolean allType = result.results().stream()
                            .allMatch(quizQuestion -> "boolean".equals(quizQuestion.type()));
                    Assertions.assertTrue(allEasy);
                    Assertions.assertTrue(allBooks);
                    Assertions.assertTrue(allType);
                })
                .expectComplete()
                .verify();
    }

    @Test
    void thatReturnCorrectSizeWithHardLevel() {
        wireMockServer.stubFor(get(urlPathEqualTo("/api.php"))
                .withQueryParam("amount", equalTo("15"))
                .withQueryParam("category", equalTo("11"))
                .withQueryParam("difficulty", equalTo("hard"))
                .withQueryParam("type", equalTo("multiple"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("wiremock/externalApiResultHard.json")));

        Flux<ExternalApiResults> externalApiResults = questionsService.fetchQuestions(11,"hard");
        StepVerifier.create(externalApiResults)
                .consumeNextWith(result -> {
                    Assertions.assertEquals(15, result.results().size());
                    boolean allEasy = result.results().stream()
                            .allMatch(quizQuestion -> "hard".equals(quizQuestion.difficulty()));
                    boolean allBooks = result.results().stream()
                            .allMatch(quizQuestion -> "Entertainment: Film".equals(quizQuestion.category()));
                    boolean allType = result.results().stream()
                            .allMatch(quizQuestion -> "multiple".equals(quizQuestion.type()));
                    Assertions.assertTrue(allEasy);
                    Assertions.assertTrue(allBooks);
                    Assertions.assertTrue(allType);
                })
                .expectComplete()
                .verify();
    }

    @Test
    void thatReturnCorrectSizeWithMediumLevel() {
        wireMockServer.stubFor(get(urlPathEqualTo("/api.php"))
                .withQueryParam("amount", equalTo("10"))
                .withQueryParam("category", equalTo("12"))
                .withQueryParam("difficulty", equalTo("medium"))
                .withQueryParam("type", equalTo("multiple"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("wiremock/externalApiResultMedium.json")));
        Flux<ExternalApiResults> externalApiResults = questionsService.fetchQuestions(12, "medium");
        StepVerifier.create(externalApiResults)
                .consumeNextWith(result -> {
                    Assertions.assertEquals(10, result.results().size());
                    boolean allEasy = result.results().stream()
                            .allMatch(quizQuestion -> "medium".equals(quizQuestion.difficulty()));
                    boolean allBooks = result.results().stream()
                            .allMatch(quizQuestion -> "Entertainment: Music".equals(quizQuestion.category()));
                    boolean allType = result.results().stream()
                            .allMatch(quizQuestion -> "multiple".equals(quizQuestion.type()));
                    Assertions.assertTrue(allEasy);
                    Assertions.assertTrue(allBooks);
                    Assertions.assertTrue(allType);
                })
                .expectComplete()
                .verify();

    }
}