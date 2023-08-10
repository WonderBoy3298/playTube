package com.wonderboy.wonderboy.service;


import com.wonderboy.wonderboy.model.User;
import com.wonderboy.wonderboy.model.Video;
import com.wonderboy.wonderboy.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {


    @Autowired
    private UserRepo userRepo ;

    public User getCurrentUser (){
        String sub =  ((Jwt)(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal())).getClaim("sub")  ;

        return   userRepo.findBySub(sub).orElseThrow(()-> new IllegalArgumentException("Cannot find " +
            "user with sub"+sub))  ;

    }

    public  void addToLikedVideo(String videoId) {

        User currentUser = getCurrentUser()  ;
        currentUser.addToLikedVideo(videoId);
        userRepo.save(currentUser) ;

    }

    public  boolean ifLikedVideo(String videoId){

        return getCurrentUser().getLikedVideo().stream().anyMatch(item -> item.equals(videoId)) ;

    }


    public boolean ifDislikedVideo(String videoId){
        return  getCurrentUser().getDislikedVideo().stream().anyMatch(item -> item.equals(videoId)) ;
    }

    public void removeFromLikedVideo(String id ){

        User currentUser = getCurrentUser()  ;
        currentUser.removeFromLikedVideo(id);
        userRepo.save(currentUser) ;

    }

    public  void removeFromDislikedVideo(String videoId){

        User currentUser = getCurrentUser();

        currentUser.removeFromDislikedVideo(videoId);

        userRepo.save(currentUser) ;

    }

    public  void  addToDislikedVideo(String videoId){
        User currentUser = getCurrentUser()  ;
        currentUser.addToDislikedVideo(videoId);
        userRepo.save(currentUser) ;
    }

    public  void  addVideoToHistory(String videoId){
        User currentUser = getCurrentUser() ;
        currentUser.addToVideoHistory(videoId)  ;
         userRepo.save(currentUser) ;
    }


    public  void subscribedUser(String userId){

        User currentUser = getCurrentUser() ;
        currentUser.subscribeUser(userId ); ;

        User user = userRepo.findById(userId).orElseThrow(()->new IllegalArgumentException("Cannot find user  ")) ;
        user.addToSubscribers(currentUser.getId()) ;

        userRepo.save(currentUser)  ;
        userRepo.save(user) ;

    }


    public  void unSubscribedUser(String userId){

        User currentUser = getCurrentUser() ;
        currentUser.removeSubscribeToUser(userId );

        User user = userRepo.findById(userId).orElseThrow(()->new IllegalArgumentException("Cannot find user  ")) ;
        user.removeFromSubscribers(currentUser.getId()) ;

        userRepo.save(currentUser)  ;
        userRepo.save(user) ;

    }

    public Set<String> userHistory(String userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Cannot find user  ")) ;

        return  user.getVideoHistory() ;


    }



}
