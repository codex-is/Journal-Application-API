package com.harsh.JournallingApplication.repository;

import com.harsh.JournallingApplication.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}


//controller -----> service -----> repository.