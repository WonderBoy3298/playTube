package com.wonderboy.wonderboy.controller;

import com.wonderboy.wonderboy.model.Video;
import com.wonderboy.wonderboy.model.VideoDto;
import com.wonderboy.wonderboy.service.StorageService;
import com.wonderboy.wonderboy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
public class VideoController {


    @Autowired
    private StorageService storageService ;

    @Autowired
    private VideoService videoService ;




    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(storageService.uploadFile(file),HttpStatus.OK) ;
    }


    @PostMapping("/thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> uploadThumbnail(@RequestParam("file") MultipartFile file
        ,@RequestParam("videoId") String videoId) throws IOException {
        return new ResponseEntity<>(storageService.uploadFile(file),HttpStatus.OK) ;
    }

    @PutMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Video> editVideo(@RequestBody VideoDto videoDto){

        return videoService.editVideo(videoDto) ;

    }


    @GetMapping("/video/{videoId}")
    public VideoDto getVideoDetails(@PathVariable String videoId ){

        return  videoService.getVideoDetails(videoId);

    }

    @PostMapping("/video/{videoId}/like")
    public VideoDto  likedVideo(@PathVariable String videoId){
            return videoService.likedVideo(videoId) ;
    }

    @PostMapping("/video/{videoId}/dislike")
    public  VideoDto dislikedVideo(@PathVariable String videoId){

        return videoService.dislikedVideo(videoId) ;

    }

    @GetMapping("/test")
    public String test(){
        return "this is a test route " ;
    }


    public List<VideoDto> getAllVideos(){

        return  videoService.getAllVideos() ;
    }




}
