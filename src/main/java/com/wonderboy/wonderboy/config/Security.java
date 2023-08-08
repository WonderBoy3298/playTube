package com.wonderboy.wonderboy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableWebSecurity
public class Security  {


    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private  String issuer ;


    @Value("${auth0.audience}")
    private  String audience ;



    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().oauth2ResourceServer().jwt()  ;

        http.cors(Customizer.withDefaults())  ;
        http.csrf().disable() ;
        return http.build() ;
     }


     @Bean
    JwtDecoder jwtDecoder(){

         NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

         OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience) ;
         OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer) ;
         OAuth2TokenValidator<Jwt> withAudience  = new DelegatingOAuth2TokenValidator<Jwt>(withIssuer);

         jwtDecoder.setJwtValidator(withAudience);

         return  jwtDecoder ;


     }


}
