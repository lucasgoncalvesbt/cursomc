package com.lucasg.cursomc.config;

import com.lucasg.cursomc.security.JWTAuthenticationFilter;
import com.lucasg.cursomc.security.JWTAuthorizationFilter;
import com.lucasg.cursomc.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private Environment env;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JWTUtil jwtUtil, Environment env) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.env = env;
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/produtos/**",
            "/categorias/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if(List.of(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors()
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and().addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService))
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
