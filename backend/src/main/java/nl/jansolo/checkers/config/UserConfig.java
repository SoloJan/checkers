package nl.jansolo.checkers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Value("${player1.user.name}")
    private String player1UserName;
    @Value("${player1.user.password}")
    private String player1Password;
    @Value("${player2.user.name}")
    private String player2UserName;
    @Value("${player2.user.password}")
    private String player2Password;

    @Bean
    UserBean player1(){
        return new UserBean(player1UserName, player1Password);
    }

    @Bean
    UserBean player2(){
        return new UserBean(player2UserName, player2Password);
    }
}
