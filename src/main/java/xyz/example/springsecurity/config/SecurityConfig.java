package xyz.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /*
     *  Configuration 01
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        /*httpSecurity.csrf(AbstractHttpConfigurer::disable);*/

        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/v1/index2").permitAll()
                                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    /*@Bean
    public  SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/v1/index2").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(formLogin -> {
                    formLogin.successHandler(successHandler()); // URL DE REDIRECCIÓN DE INICIAR SESIÓN
                    formLogin.permitAll();
                })
                .sessionManagement(sessionManager -> {
                    sessionManager.sessionCreationPolicy(SessionCreationPolicy.ALWAYS); // ALWAYS - IF_REQUIRED - NEVER - STATELESS
                    sessionManager.invalidSessionUrl("/login");
                    sessionManager.maximumSessions(1);
                    sessionManager.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession); // migrateSession - newSession
                    sessionManager.sessionConcurrency(sessionConcurrency -> {
                        sessionConcurrency.expiredUrl("/login");
                        sessionConcurrency.sessionRegistry(sessionRegistry());
                    });
                })
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }*/

    public AuthenticationSuccessHandler successHandler(){
        return (((request, response, authentication) -> response.sendRedirect("/v1/session")));
    }

    /**
     * Devolver datos de la sessión
     */
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

}
