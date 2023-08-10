package com.wonderboy.wonderboy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "video") @Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {


    @Id
    private  String id ;
    private  String title ;
    private  String description ;
    private  String userId ;

    private AtomicInteger likes  = new AtomicInteger(0) ;
    private  AtomicInteger dislikes = new AtomicInteger(0);
    private Set<String> tags ;

    private  String videoUrl ;

    private  AtomicInteger viewCount = new AtomicInteger(0) ;
    private  String thumbnailUrl ;

    private VideoStatus videoStatus ;

    private List<Comment> commentList = new CopyOnWriteArrayList<>() ;


    public void   addComment(Comment comment ){
            commentList.add(comment) ;
    }


    public void  incrementsLikes(){
        likes.incrementAndGet() ;
    }

    public void decrementLikes(){
    likes.decrementAndGet()  ;
    }

    public   void incrementDislikes(){
        dislikes.incrementAndGet() ;
    }

    public   void decrementDislikes(){
        dislikes.decrementAndGet() ;
    }


    public void increaseVideoCount(){
        viewCount.incrementAndGet() ;
    }

}
