package com.wonderboy.wonderboy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(value = "video") @Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {


    @Id
    private  String id ;
    private  String title ;
    private  String description ;
    private  String userId ;
    private  Integer likes  ;
    private  Integer dislikes ;
    private Set<String> tags ;
    private  String videoUrl ;

    private  Integer viewCount ;
    private  String thumbnailUrl ;

    private VideoStatus videoStatus ;



}
