package com.wonderboy.wonderboy.controller;


import com.wonderboy.wonderboy.model.Comment;
import com.wonderboy.wonderboy.model.CommentDto;
import com.wonderboy.wonderboy.service.UserRegistrationService;
import com.wonderboy.wonderboy.service.UserService;
import com.wonderboy.wonderboy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private  UserRegistrationService userRegistrationService ;

    @Autowired
    private UserService userService ;

    @Autowired
    private VideoService videoService ;
    @GetMapping("/user/register")
    public ResponseEntity<String> register(Authentication authentication) throws IOException, InterruptedException {

        Jwt jwt =(Jwt) authentication.getPrincipal();

        return  userRegistrationService.registerUser(jwt.getTokenValue());


    }

    @PostMapping("subscribe/{userId}")
    public boolean subscribeUser(@PathVariable String userId){

        userService.subscribedUser(userId); ;
        return  true ;
    }

    @PostMapping("unsubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unSubscribeUser(@PathVariable String userId){

        userService.unSubscribedUser(userId) ;
        return  true ;

    }




    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public void addComment(@PathVariable String videoId, @RequestBody CommentDto commentDto){

        videoService.addComment(videoId,commentDto) ;

    }

    @GetMapping("{videoId}/comment")
    public List<CommentDto> getAllComments(@PathVariable String videoId){

        return videoService.getAllComments(videoId);
    }



    public Set<String> userHistory(@PathVariable String userId){
        return  userService.userHistory(userId) ;
    }



}