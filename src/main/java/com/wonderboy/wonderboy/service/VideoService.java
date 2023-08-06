package com.wonderboy.wonderboy.service;

import com.wonderboy.wonderboy.model.Video;
import com.wonderboy.wonderboy.model.VideoDto;
import com.wonderboy.wonderboy.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    @Autowired
    private VideoRepo videoRepo ;

    public ResponseEntity<Video> editVideo(VideoDto videoDto ){


    Video video  =  videoRepo.findById(videoDto.getId()).orElseThrow(()->
                new IllegalArgumentException("Cannot find the video "));


    video.setTitle(videoDto.getTitle());
    video.setDescription(videoDto.getDescription()) ;
    video.setTags(videoDto.getTags());
    video.setThumbnailUrl(videoDto.getThumbnail());
    video.setVideoStatus(videoDto.getVideoStatus());


    videoRepo.save(video);
    return new ResponseEntity<>(video, HttpStatus.OK);
    }




    public  VideoDto getVideoDetails(String videoId){

        Video video =  videoRepo.findById(videoId).orElseThrow(()->new IllegalArgumentException("video Not Found"));

        VideoDto videoDto = new VideoDto();

        videoDto.setVideoUrl(video.getVideoUrl());
        videoDto.setThumbnail(video.getThumbnailUrl());
        videoDto.setVideoStatus(video.getVideoStatus());
        videoDto.setTitle(video.getTitle());
        videoDto.setDescription(video.getDescription());
        videoDto.setTags(video.getTags());
        videoDto.setId(video.getId());


        return videoDto ;
    }


}
