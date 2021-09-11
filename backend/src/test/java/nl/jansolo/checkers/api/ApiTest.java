package nl.jansolo.checkers.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public abstract class ApiTest {

    private final static String BASE_URL = "http://localhost:6643";

    @BeforeEach
    void configureRestAssured(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Value("${player1.user.name}")
    private String player1UserName;
    @Value("${player1.user.password}")
    private String player1Password;
    @Value("${player2.user.name}")
    private String player2UserName;
    @Value("${player2.user.password}")
    private String player2Password;


    protected String getUrl(String endpoint){
        return BASE_URL + endpoint;
    }

    protected void unauthorisedUserShouldNotHaveAccess(String url){
        given()
                .auth().basic("unknown user", "password")
        .when()
                .get(url)
        .then()
                .statusCode(401);
    }

    protected RequestSpecification givenPlayer1IsLoggedIn() {
        return given()
                .auth().basic(player1UserName, player1Password);
    }

    protected RequestSpecification givenPlayer2IsLoggedIn() {
        return given()
                .auth().basic(player2UserName, player2Password);
    }


}
