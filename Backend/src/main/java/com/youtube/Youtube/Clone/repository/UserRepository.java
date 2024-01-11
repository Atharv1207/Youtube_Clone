package com.youtube.Youtube.Clone.repository;

import com.youtube.Youtube.Clone.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
