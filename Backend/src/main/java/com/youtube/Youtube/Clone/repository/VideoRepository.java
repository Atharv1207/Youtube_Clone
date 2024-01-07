package com.youtube.Youtube.Clone.repository;

import com.youtube.Youtube.Clone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {

}
