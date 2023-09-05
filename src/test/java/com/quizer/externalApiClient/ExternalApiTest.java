package com.quizer.externalApiClient;

import com.quizer.externalApiClient.config.WiremockConfig;
import com.quizer.model.QuizQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;


class ExternalApiTest extends WiremockConfig {
    @Autowired
    private openTdbClientImpl tdbClient;

    @Test
    void thatReturnCorrectQuizQuestionObject(){
        wireMockServer.stubFor(get(urlPathEqualTo("/api.php"))
                .withQueryParam("amount", equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("wiremock/externalApiResult.json")));

        QuizQuestion quizQuestion = tdbClient.fetchQuestions();
        System.out.println(quizQuestion);

        assertEquals(
                "Who is the villain company in &quot;Stardew Valley&quot;?",
                quizQuestion.question());
        assertEquals(
                "Joja Co ",
                quizQuestion.correct_answer());
        assertEquals(
                "Entertainment: Video Games",
                quizQuestion.category());
        assertEquals(3, quizQuestion.incorrect_answers().size());
        assertEquals("Ronin", quizQuestion.incorrect_answers().get(0));
    }
}