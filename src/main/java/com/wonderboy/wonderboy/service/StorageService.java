package com.wonderboy.wonderboy.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.wonderboy.wonderboy.model.Video;
import com.wonderboy.wonderboy.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private  String bucketName ;

    @Autowired
    private AmazonS3 s3Client ;

    @Autowired
    private VideoRepo videoRepo ;

    public  String uploadFile(MultipartFile file) throws FileNotFoundException {


        File fileObject = convertMultipartFileToFile(file) ;
        String fileName = UUID.randomUUID().toString()+"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObject)) ;

        Video video=new Video() ;
        video.setVideoUrl(fileName);
        videoRepo.save(video) ;
        fileObject.delete() ;

        return  "File Uploaded "+ fileName ;

    }



    private File convertMultipartFileToFile( MultipartFile file ) throws FileNotFoundException {

        File convertedFile = new File( file.getOriginalFilename()) ;
        try(FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write( file.getBytes() )  ;
        } catch (IOException e) {
            throw new RuntimeException(e) ;
        }
        return convertedFile ;

    }




}
