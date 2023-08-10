package com.wonderboy.wonderboy.service;

import com.wonderboy.wonderboy.model.Comment;
import com.wonderboy.wonderboy.model.CommentDto;
import com.wonderboy.wonderboy.model.Video;
import com.wonderboy.wonderboy.model.VideoDto;
import com.wonderboy.wonderboy.repo.UserRepo;
import com.wonderboy.wonderboy.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    private VideoRepo videoRepo ;
    @Autowired
     private UserService userService ;

    @Autowired
    private UserRepo userRepo ;

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


    public void increaseVideoCount(Video savedVideo){
        savedVideo.increaseVideoCount();
        videoRepo.save(savedVideo) ;
    }




    private VideoDto mapToVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnail(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDislikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        return videoDto;
    }


    public  VideoDto getVideoDetails(String videoId){

        Video video =  videoRepo.findById(videoId).orElseThrow(()->new IllegalArgumentException("video Not Found"));

        increaseVideoCount(video) ;
        userService.addVideoToHistory(videoId) ;

        VideoDto videoDto = mapToVideoDto(video) ;

        return videoDto ;

    }


    public  VideoDto likedVideo(String videoId){

        Video video  =  videoRepo.findById(videoId).orElseThrow(()->new IllegalArgumentException("video not found")) ;

        //if the user alerady liked the video we have to make it to 0

        if (userService.ifLikedVideo(videoId)){

            video.decrementLikes();
            userService.removeFromLikedVideo(videoId) ;

        } else if (userService.ifDislikedVideo(videoId)) {

            video.decrementDislikes();
            userService.removeFromDislikedVideo(videoId);
            video.incrementsLikes() ;
            userService.addToLikedVideo(videoId) ;
        }else {

            video.incrementsLikes();
            userService.addToLikedVideo(videoId);

        }

        videoRepo.save(video)  ;


        VideoDto videoDto ;
        return videoDto = mapToVideoDto(video) ;


    }


    public VideoDto dislikedVideo(String videoId){


        Video video  =  videoRepo.findById(videoId).orElseThrow(()->new IllegalArgumentException("video not found")) ;

        //if the user alerady liked the video we have to make it to 0

        if (userService.ifDislikedVideo(videoId)){

            video.decrementDislikes();
            userService.removeFromDislikedVideo(videoId) ;

        } else if (userService.ifLikedVideo(videoId)) {

            video.decrementLikes();
            userService.removeFromLikedVideo(videoId);
            video.incrementDislikes(); ;
            userService.addToDislikedVideo(videoId) ;
        }else {

            video.incrementsLikes();
            userService.addToLikedVideo(videoId);

        }

        videoRepo.save(video)  ;


        VideoDto videoDto = new VideoDto();

        return videoDto = mapToVideoDto(video)  ;

    }


    public void addComment(String videoId, CommentDto commentDto){


        Video video = videoRepo.findById(videoId).orElseThrow(()-> new IllegalArgumentException("Video Not Found ")) ;


        Comment comment = new Comment() ;

        comment.setText(commentDto.getCommentText());
        comment.setAuthorId(commentDto.getAuthorId());

        video.addComment(comment);

        videoRepo.save(video) ;


    }

    public List<CommentDto> getAllComments(String videoId){


        Video video = videoRepo.findById(videoId).orElseThrow(()-> new IllegalArgumentException("video Not Found")) ;

       List<Comment> commentList = video.getCommentList() ;

        return  commentList.stream().map(comment -> mapToCommentDto(comment)).toList();



    }
    public  CommentDto mapToCommentDto(Comment comment){

        CommentDto commentDto = new CommentDto() ;
        commentDto.setCommentText(comment.getText());
        commentDto.setAuthorId(comment.getAuthorId());

        return commentDto ;
    }


    public List<VideoDto> getAllVideos(){

        return videoRepo.findAll().stream().map(video -> mapToVideoDto(video)).toList() ;

    }

}
