package nl.jansolo.checkers.security;


import lombok.RequiredArgsConstructor;
import nl.jansolo.checkers.config.UserBean;
import nl.jansolo.checkers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class DefaultWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ROLE_PLAYER = "player";
    private static final String ROLE_DEVELOPER = "developer";
    @Value("${developer.user.name}")
    private String developerUserName;
    @Value("${developer.user.password}")
    private String developerPassword;
    private final UserService userService;

    /**
     * Configures security for HTTP
     * Cors and csrf are disabled which should not be done in production
     * The player role can start a game and do a move
     * The swagger documentation is available to everybody
     * The H2 database is only accessible by developers
     * @param http {@link HttpSecurity} instance to configure
     * @throws Exception exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers( "/h2-console/**").hasRole(ROLE_DEVELOPER)
                .antMatchers("/v3/api-docs/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers( "/checker/**").hasRole(ROLE_PLAYER)
                .antMatchers( "/user/opponent").hasRole(ROLE_PLAYER)
                .anyRequest().denyAll()
                .and()
                .httpBasic();
    }

    /**
     * loads all players defined in the UserConfig into an in memory database and give them the role player
     * also loads a developer role who can access the database
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        List<UserBean> players = userService.findAllUsers();
        for(UserBean player: players){
            auth.inMemoryAuthentication().withUser(player.getName()).password("{noop}"+ player.getPassword()).roles(ROLE_PLAYER);
        }
        auth.inMemoryAuthentication().withUser(developerUserName).password("{noop}"+ developerPassword).roles(ROLE_DEVELOPER);
    }

}
