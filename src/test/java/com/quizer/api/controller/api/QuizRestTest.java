package com.quizer.api.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizer.externalApiClient.config.RestAssuredIntegrationTestBase;
import com.quizer.infrastructure.model.QuizQuestion;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static com.quizer.api.controller.api.QuizRest.API;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QuizRestTest extends RestAssuredIntegrationTestBase {

    @Test
    public void thatReturnBadRequestWhenWrongDifficulty() {
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get("http://localhost:" + port + basePath + API + "/films/testDifficulty")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .extract().response();

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        assertThat(jsonPath.getString("statusCode")).isEqualTo("BadRequest");
        assertThat(jsonPath.getString("error")).isEqualTo("Invalid difficulty: testDifficulty. You can choose from three options: easy, medium or hard");
    }

    @Test
    public void thatReturnBadRequestWhenWorngCategory() {
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get("http://localhost:" + port + basePath + API + "/testCategory/medium")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .extract().response();

        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);

        assertThat(jsonPath.getString("statusCode")).isEqualTo("BadRequest");
        assertThat(jsonPath.getString("error")).isEqualTo("Invalid category: testCategory. " +
                "You can choose from five options: random, books, films, music, television, computers");
    }

    @ParameterizedTest
    @CsvSource({
            "books",
            "films",
            "music",
            "television",
            "computers",
            "random"

    })
    public void thatReturnCorrectListWithQuestionWhenEasyDifficulty(String category) throws JsonProcessingException {
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get("http://localhost:" + port + basePath + API + "/" + category + "/easy")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();


        String responseBody = response.getBody().asString();


        ObjectMapper objectMapper = new ObjectMapper();
        List<QuizQuestion> quizQuestions = objectMapper.readValue(responseBody, new TypeReference<List<QuizQuestion>>() {
        });

        for (QuizQuestion quizQuestion : quizQuestions) {
            Assertions.assertEquals(quizQuestion.type(), "boolean");
            Assertions.assertEquals(quizQuestion.difficulty(), "easy");
        }
        Assertions.assertEquals(5, quizQuestions.size());
    }

    @ParameterizedTest
    @CsvSource({
            "books",
            "films",
            "music",
            "television",
            "computers",
            "random"

    })
    public void thatReturnCorrectListWithQuestionWhenMediumDifficulty(String category) throws JsonProcessingException {
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get("http://localhost:" + port + basePath + API + "/" + category + "/medium")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();


        String responseBody = response.getBody().asString();


        ObjectMapper objectMapper = new ObjectMapper();
        List<QuizQuestion> quizQuestions = objectMapper.readValue(responseBody, new TypeReference<List<QuizQuestion>>() {
        });

        for (QuizQuestion quizQuestion : quizQuestions) {
            Assertions.assertEquals(quizQuestion.type(), "multiple");
            Assertions.assertEquals(quizQuestion.difficulty(), "medium");
        }
        Assertions.assertEquals(10, quizQuestions.size());
    }

    @ParameterizedTest
    @CsvSource({
            "books",
            "films",
            "music",
            "television",
            "computers",
            "random"

    })
    public void thatReturnCorrectListWithQuestionWhenHardDifficulty(String category) throws JsonProcessingException {
        Response response = given()
                .spec(requestSpecification())
                .when()
                .get("http://localhost:" + port + basePath + API + "/" + category + "/hard")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();


        String responseBody = response.getBody().asString();


        ObjectMapper objectMapper = new ObjectMapper();
        List<QuizQuestion> quizQuestions = objectMapper.readValue(responseBody, new TypeReference<List<QuizQuestion>>() {
        });

        for (QuizQuestion quizQuestion : quizQuestions) {
            Assertions.assertEquals(quizQuestion.type(), "multiple");
            Assertions.assertEquals(quizQuestion.difficulty(), "hard");
        }
        Assertions.assertEquals(15, quizQuestions.size());
    }

    @Test
    public void thatReturnScoreboard(){
        given()
                .spec(requestSpecification())
                .when()
                .get("http://localhost:" + port + basePath + API + "/scoreboard")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
    @Test
    public void thatReturnNotFountWhenPlayerInfoByWrongUsername(){
        given()
                .spec(requestSpecification())
                .when()
                .get("http://localhost:" + port + basePath + API + "/game-info/test")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON);
    }
}