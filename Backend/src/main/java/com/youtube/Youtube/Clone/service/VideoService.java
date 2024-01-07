package com.youtube.Youtube.Clone.service;

import com.youtube.Youtube.Clone.model.Video;
import com.youtube.Youtube.Clone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    @Autowired
    private final S3service s3service;

    private final VideoRepository videoRepository;
    public void uploadVideo(MultipartFile file){
        //upload video to S3
        String videoUrl = s3service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        // save Video data to db
        videoRepository.save(video);
    }
}
