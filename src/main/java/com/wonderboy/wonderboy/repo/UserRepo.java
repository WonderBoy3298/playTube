package com.wonderboy.wonderboy.repo;

import com.wonderboy.wonderboy.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User,String> {


    Optional<User> findBySub(String sub) ;

}
