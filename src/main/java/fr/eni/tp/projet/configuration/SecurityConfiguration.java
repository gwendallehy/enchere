package fr.eni.tp.projet.configuration;

import org.apache.tomcat.Jar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo AS username, password, 1 FROM MEMBRE where pseudo=?;");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT m.email AS username, r.ROLE FROM ROLES r INNER JOIN MEMBRE m ON m.admin = r.IS_ADMIN where email=?;");
        return jdbcUserDetailsManager;
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                    .anyRequest().permitAll();

        });

        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/films");
        });

        http.logout(logout -> {
            logout
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/");
        });
        return http.build();

    }
}
