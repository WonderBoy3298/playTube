package com.wonderboy.wonderboy.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonderboy.wonderboy.dtos.UserInfoDTO;
import com.wonderboy.wonderboy.model.User;
import com.wonderboy.wonderboy.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserRegistrationService {


    @Value("${auth0.userinfoEndpoint}")
    private  String userInfoEndPoint ;



    @Autowired
    private UserRepo userRepo ;

    public ResponseEntity<String>  registerUser(String tokenValue) throws IOException, InterruptedException {

        HttpRequest httpRequest  = HttpRequest.newBuilder().GET().uri(URI.create(userInfoEndPoint))
                .setHeader("Authorization",String.format("Bearer %s",tokenValue)).build() ;


       HttpClient httpClient = HttpClient.newBuilder()
               .version(HttpClient.Version.HTTP_2).build();


        HttpResponse<String> httpResponse =  httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString()) ;

        String body = httpResponse.body() ;


        ObjectMapper objectMapper = new ObjectMapper() ;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false) ;
        UserInfoDTO userInfoDTO =  objectMapper.readValue(body, UserInfoDTO.class) ;

        User user = new User();
        user.setFirstName(userInfoDTO.getGivenName());
        user.setLastName(userInfoDTO.getFamilyName());
        user.setFullName(userInfoDTO.getName());
        user.setEmailAddress(userInfoDTO.getEmail());

        userRepo.save(user);



        return new ResponseEntity<>("User Created Successfuly on the DataBase ", HttpStatus.OK);

    }



}
