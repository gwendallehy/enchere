package fr.eni.tp.projet.configuration;

import org.apache.tomcat.Jar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.security.Security;

@Configuration
public class SecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo AS username, password, 1 FROM USERS WHERE pseudo=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT m.pseudo AS username, r.ROLE FROM ROLES r INNER JOIN USERS m ON m.administrator = r.IS_ADMIN WHERE pseudo=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/users/create").permitAll()
                            .requestMatchers(HttpMethod.GET, "/profile").authenticated()  // Ensure profile is secured
                            .requestMatchers(HttpMethod.GET, "/users/profile").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/users/viewProfile").hasRole("USER")

                            .requestMatchers(HttpMethod.GET, "/users/update/").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/users/update").hasRole("USER")

                            .requestMatchers(HttpMethod.GET, "/users/delete/").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/users/delete").hasRole("USER")

                            .requestMatchers(HttpMethod.GET, "/auctions/create").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/auctions/create").hasRole("USER")

                            .requestMatchers(HttpMethod.GET, "/auctions/cancel").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/auctions/cancel").hasRole("USER")

                            .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET, "/users/create").permitAll()
                            .requestMatchers(HttpMethod.POST, "/users/create").permitAll()

                            .requestMatchers(HttpMethod.GET, "/auctions/list").permitAll()
                            .requestMatchers(HttpMethod.POST, "/auctions/list").permitAll()

                            .requestMatchers(HttpMethod.GET, "/auctions/view").permitAll()
                            .requestMatchers(HttpMethod.POST, "/auctions/view").permitAll()








                            .anyRequest().permitAll();
                })
                .formLogin(formLogin -> formLogin
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/auctions/list", true))  // New syntax with Customizer
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
