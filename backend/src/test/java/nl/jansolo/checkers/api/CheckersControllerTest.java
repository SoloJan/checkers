package nl.jansolo.checkers.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import nl.jansolo.checkers.api.dto.Color;
import org.junit.jupiter.api.Test;

import static nl.jansolo.checkers.api.UserApiTest.PLAYER_1;
import static nl.jansolo.checkers.api.UserApiTest.PLAYER_2;
import static nl.jansolo.checkers.api.dto.Color.BLACK;
import static nl.jansolo.checkers.api.dto.Color.WHITE;
import static nl.jansolo.checkers.api.dto.Status.PLAYING;
import static org.hamcrest.Matchers.equalTo;


class CheckersControllerTest extends ApiTest {

    private String getBaseUrl(){
        return getUrl("/checkers");
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

    /**
     * We do a game with three moves, first white moves, than black, than white hits black
     * We assert the value of the board at (3,2) the coordinate where black starts and white ends up
     */
    @Test
    void playAGame(){
        startGame(PLAYER_2, WHITE);
        moveAsPlayer1(6,5, 5,4)
                .then()
                .statusCode(200)
                .body("board[3][2]", equalTo(BLACK.toString()));
        moveAsPlayer2(3,2, 4,3)
                .then()
                .statusCode(200)
                .body("board[3][2]", equalTo(""));
        moveAsPlayer1(5,4, 3,2)
                .then()
                .statusCode(200)
                .body("board[3][2]", equalTo(WHITE.toString()));
    }


    @Test
    void doInvalidMove(){
        startGame(PLAYER_2, WHITE);
        moveAsPlayer1(6,5, 6,5)
                .then()
                .statusCode(400)
                .body("message", equalTo("The move you are doing is not valid"));
    }

    @Test
    void playWhenItsNotYourTurn(){
        startGame(PLAYER_2, BLACK);
        moveAsPlayer1(6,5, 5,4)
                .then()
                .statusCode(403)
                .body("message", equalTo("Its not your turn wait for the other player to move, or the game has ended"));
    }

    @Test
    void playWithNonExistingStone(){
        startGame(PLAYER_2, WHITE);
        moveAsPlayer1(4,4, 5,5)
                .then()
                .statusCode(404)
                .body("message", equalTo("The stone you are trying to move does not exist, or does not belong to the player"));
    }


    @Test
    void playWhenThereIsNoGame(){
        startGame(PLAYER_2, WHITE);
        deletePreviousGameForPlayer1();
        moveAsPlayer1(4,4, 5,5)
                .then()
                .statusCode(404)
                .body("message", equalTo("There is no game going on for player player1 please start the game first"));
    }


    private Response moveAsPlayer1(int fromRow, int fromColumn, int toRow, int toColumn) {
        return move(givenPlayer1IsLoggedIn(), fromRow, fromColumn, toRow, toColumn);
    }

    private Response moveAsPlayer2(int fromRow, int fromColumn, int toRow, int toColumn) {
        return move(givenPlayer2IsLoggedIn(), fromRow, fromColumn, toRow, toColumn);
    }

    private Response move(RequestSpecification givenLoggedInPlayer,  int fromRow, int fromColumn, int toRow, int toColumn){
       return givenLoggedInPlayer
                .body("{\n" +
                "  \"from\": {\n" +
                "    \"row\": "+ fromRow +",\n" +
                "    \"column\": "+ fromColumn +"\n" +
                "  },\n" +
                "  \"to\": {\n" +
                "    \"row\": "+ toRow +",\n" +
                "    \"column\": "+ toColumn +"\n" +
                "  }\n" +
                "}"
        )
                .contentType(ContentType.JSON)
                .when()
                .put(getBaseUrl());
    }

    private Response startGame(String opponentName, Color color) {
        return givenPlayer1IsLoggedIn()
                .body("{ \"opponentName\" : \""+ opponentName + "\"," +
                        "  \"colorToPlayWith\": \""+ color + "\"} ")
                .contentType(ContentType.JSON)
                .when()
                .post(getBaseUrl());
    }

    private Response deletePreviousGameForPlayer1() {
        return givenPlayer1IsLoggedIn()
                .when()
                .delete(getBaseUrl());
    }



}