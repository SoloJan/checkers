package nl.jansolo.checkers.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import nl.jansolo.checkers.api.dto.Color;
import org.junit.jupiter.api.Test;

import static nl.jansolo.checkers.api.UserApiTest.PLAYER_1;
import static nl.jansolo.checkers.api.UserApiTest.PLAYER_2;
import static nl.jansolo.checkers.api.dto.Color.BLACK;
import static nl.jansolo.checkers.api.dto.Color.WHITE;
import static nl.jansolo.checkers.api.dto.Status.PLAYING;
import static org.hamcrest.Matchers.equalTo;


class CheckerControllerTest extends ApiTest {




    private String getBaseUrl(){
        return getUrl("/checker");
    }


    @Test
    public void unauthorisedUserShouldNotHaveAccess(){
        unauthorisedUserShouldNotHaveAccess(getBaseUrl());
    }


    @Test
    void startAGame() {
        startGame(PLAYER_2 , WHITE)
                .then()
                .statusCode(200)
                .body("you.name", equalTo(PLAYER_1))
                .body("you.color", equalTo(WHITE.toString()))
                .body("you.myTurn", equalTo(true))
                .body("you.status", equalTo(PLAYING.toString()))
                .body("opponent.name", equalTo(PLAYER_2))
                .body("opponent.color", equalTo(BLACK.toString()))
                .body("opponent.myTurn", equalTo(false))
                .body("opponent.status", equalTo(PLAYING.toString()))
                .body("board[0][0]", equalTo(""))
                .body("board[0][1]", equalTo(BLACK.toString()))
                .body("board[9][8]", equalTo(WHITE.toString()));

    }

    @Test
    void startWithNonExistingOpponent(){
        startGame("non existing user", WHITE)
            .then()
                .statusCode(404)
                .body("message", equalTo("non existing user is not a valid opponent, " +
                        "please use the user/opponents api to see a list of all available opponents"));

    }


    @Test
    void startTheSameGameTwice(){
        startGame(PLAYER_2, WHITE);
        startGame(PLAYER_2, WHITE)
                .then()
                .statusCode(200);
    }

    private Response startGame(String opponentName, Color color) {
        return givenPlayer1IsLoggedIn()
                .body("{ \"opponentName\" : \""+ opponentName + "\"," +
                        "  \"colorToPlayWith\": \""+ color + "\"} ")
                .contentType(ContentType.JSON)
                .when()
                .post(getBaseUrl());
    }



}