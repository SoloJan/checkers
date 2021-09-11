package nl.jansolo.checkers.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiSwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityRequirement requirement = new SecurityRequirement().addList("basicScheme");
        List<SecurityRequirement> req = new ArrayList<>();
        req.add(requirement);
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                            new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))).security(req)
                .info(new Info().title("Checkers for Nerds API").contact(new Contact().name("Jan Ruijtenberg")
                                .email("jan.ruijtenberg@hotmail.com"))
                        .description("This represents a very simple checkers game, not all rules of actual checkers are " +
                                "implemented yet. You can not do multiple hits, and you can not get a king. It implements" +
                                "the Dutch rules for the games which means a board from 10 by 10." +
                                "There is a user api which can help you to find a opponent to invite for a game. " +
                                "And there is a checkers api where you can start a game or do a move. " +
                                "Exceptions are raised if you do an invalid move " +
                                "You can either login as player1 with password player1 or as player2 with password player2. " +
                                "If player1 did a move its up to player2 to move. The game finishes when one of the players " +
                                "can not move anymore and the api will let you know who won . " +
                                "You can also end the game with an api call or start a new one, in that case there is " +
                                "no winner"));
    }
}
