package com.wonderboy.wonderboy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User") @Data @AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private String sub;
    private Set<String> subscribedToUsers  = ConcurrentHashMap.newKeySet();
    private Set<String> subscibres = ConcurrentHashMap.newKeySet() ;
    private Set<String> videoHistory = ConcurrentHashMap.newKeySet()  ;
    private Set<String> likedVideo  =  ConcurrentHashMap.newKeySet();
    private  Set<String> dislikedVideo = ConcurrentHashMap.newKeySet() ;



    public  void  addToLikedVideo(String videoId){
        likedVideo.add(videoId) ;
    }


    public  void addToDislikedVideo(String videoId){
        dislikedVideo.add(videoId) ;
    }

    public  void  removeFromLikedVideo(String videoId){
        likedVideo.remove(videoId) ;
    }

    public void  removeFromDislikedVideo(String videoId) {

        dislikedVideo.remove(videoId) ;
    }

    public  void addToVideoHistory(String id){
        videoHistory.add(id)  ;

    }


    public void subscribeUser(String userId){
        subscibres.add(userId) ;
    }


    public void addToSubscribers(String id ) {
         subscibres.add(id)  ;
    }



    public void removeSubscribeToUser(String id){
        subscribedToUsers.remove(id) ;
    }


    public void removeFromSubscribers(String id ) {
        subscibres.remove(id) ;
    }


}
