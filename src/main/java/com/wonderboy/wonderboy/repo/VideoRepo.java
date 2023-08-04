package com.wonderboy.wonderboy.repo;

import com.wonderboy.wonderboy.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepo extends MongoRepository<Video,String> {
}
