package com.itacademy.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.itacademy.database.entity.Role.ADMIN;
import static com.itacademy.database.entity.Role.USER;
import static com.itacademy.web.util.UrlPath.API;
import static com.itacademy.web.util.UrlPath.LOGIN_PATH;
import static com.itacademy.web.util.UrlPath.LOGOUT_PATH;
import static com.itacademy.web.util.UrlPath.PROFESSOR_FILTER_PATH;
import static com.itacademy.web.util.UrlPath.STUDENTS_LIST_PATH;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers(API + PROFESSOR_FILTER_PATH)
                        .hasAnyAuthority(USER.name(), ADMIN.name())
                    .antMatchers(API + STUDENTS_LIST_PATH)
                        .hasAuthority(USER.name())
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage(API + LOGIN_PATH)
                    .failureUrl(API + LOGIN_PATH + "?error")
                    .defaultSuccessUrl(API + PROFESSOR_FILTER_PATH)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl(API + LOGOUT_PATH)
                    .logoutSuccessUrl(API + LOGIN_PATH + "?logout")
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }
}
