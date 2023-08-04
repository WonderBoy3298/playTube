package com.wonderboy.wonderboy.config;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${spring.cloud.aws.credentials.access-key}")
    private  String accessKey ;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private  String publickey ;

    @Value("${spring.cloud.aws.region.static}")
    private  String region ;


    @Bean
    AmazonS3 generateS3Client(){

        AWSCredentials credentials =new BasicAWSCredentials(accessKey,publickey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build() ;


    }

}
