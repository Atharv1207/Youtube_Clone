package com.youtube.Youtube.Clone.service;

import com.youtube.Youtube.Clone.dto.VideoDto;
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

    public VideoDto editVideo(VideoDto videoDto){
        Video savedVideo = getVideoById(videoDto.getId());
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());

        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId){

        var savedVideo = getVideoById(videoId);
        s3service.uploadFile(file);
        String thumbnailUrl = s3service.uploadFile(file);
        savedVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(savedVideo);
        return thumbnailUrl;
    }

    private Video getVideoById(String videoId){
        return videoRepository.findById(videoId).orElseThrow(() -> new IllegalArgumentException("Cannot find video by id " + videoId));

    }
}
