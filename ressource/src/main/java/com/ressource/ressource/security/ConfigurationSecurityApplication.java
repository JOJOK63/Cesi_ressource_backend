package com.ressource.ressource.security;

import com.ressource.ressource.enumeration.Role;
import com.ressource.ressource.service.Impl.UserServiceImpl;
import com.ressource.ressource.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurityApplication {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;
    @Autowired
    public ConfigurationSecurityApplication(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    // Accepter les URL externes
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize ->
                                authorize.requestMatchers(POST,"/signup").permitAll()
//                                        .requestMatchers(POST,"/activation").permitAll()
                                        .requestMatchers(GET,"/disconnect").permitAll()
                                        .requestMatchers(POST,"/login").permitAll()
                                        .requestMatchers(GET,"/context").authenticated()
                                        .requestMatchers(GET,"/ressources").permitAll()
                                        .requestMatchers(POST,"/ressources").authenticated()
                                        .requestMatchers(PUT,"/ressources/{id}").authenticated()
                                        .requestMatchers(DELETE,"/ressources/{id}").authenticated()
                                        .requestMatchers(GET,"/ressources/{id}").permitAll()
                                        .requestMatchers(GET, "/commentary").permitAll()
                                        .requestMatchers(POST, "/commentary/{id}").authenticated()
                                        .requestMatchers(POST, "/commentary/{idRessource}/{idComment}").authenticated()
                                        .requestMatchers(GET, "/categories").permitAll()
                                        .requestMatchers(POST, "/categories").authenticated()
                                        .requestMatchers(PUT, "/categories/{id}").authenticated()
                                        .requestMatchers(DELETE, "/categories/{id}").authenticated()
                                        .requestMatchers(GET,"/users").permitAll()
                                        .anyRequest().authenticated()
                                        )
                                        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        )
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(this.bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public RequestCache refererRequestCache() {
        return new HttpSessionRequestCache() {
            @Override
            public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
                String referrer = request.getHeader("referer");
                if (referrer == null) {
                    referrer = request.getRequestURL().toString();
                }
                request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST",
                        new SimpleSavedRequest(referrer));

                System.out.println(request);

            }
        };
    }

}
