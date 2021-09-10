package nl.jansolo.checkers.api;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;


class UserApiTest extends ApiTest {

    static final String PLAYER_2 = "player2";
    static final String PLAYER_1 = "player1";

    private String getBaseUrl(){
        return getUrl("/user/opponent");
    }

    @Test
    public void unauthorisedUserShouldNotHaveAccess(){
        unauthorisedUserShouldNotHaveAccess(getBaseUrl());
    }

    @Test
    public void playerShouldGetListOfOpponents(){
        givenPlayer1IsLoggedIn()
        .when()
                .get(getBaseUrl())
        .then()
                .statusCode(200)
                .body("$.size", equalTo(1))
                .body("[0].name", equalTo(PLAYER_2));
    }


}