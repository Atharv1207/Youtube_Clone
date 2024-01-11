package com.youtube.Youtube.Clone.service;

import com.youtube.Youtube.Clone.dto.UploadVideoResponse;
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

    private final UserService userService;

    private final VideoRepository videoRepository;
    public UploadVideoResponse uploadVideo(MultipartFile file){
        //upload video to S3
        String videoUrl = s3service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        // save Video data to db
        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
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

    public VideoDto getVideoDetails(String videoId){

        Video savedVideo = getVideoById(videoId);
        VideoDto videoDto = new VideoDto();
        videoDto.setTitle(savedVideo.getTitle());
        videoDto.setDescription(savedVideo.getDescription());
        videoDto.setTags(savedVideo.getTags());
        videoDto.setThumbnailUrl(savedVideo.getThumbnailUrl());
        videoDto.setVideoStatus(savedVideo.getVideoStatus());
        videoDto.setId(savedVideo.getId());
        videoDto.setVideoUrl(savedVideo.getVideoUrl());

        return videoDto;
    }


    public VideoDto likeVideo(String videoId) {

        Video videoById = getVideoById(videoId);

        if(userService.ifLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
        }
        else if(userService.ifDisLikedVideo(videoId)){
            videoById.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }
        else{
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }

        VideoDto videoDto = new VideoDto();
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setId(videoById.getId());
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setLikedCount(videoById.getLikes().get());
        videoDto.setDislikedCount(videoById.getLikes().get());

        videoById.incrementLikes();
        userService.addToLikedVideos(videoId);
        return videoDto;
    }
}
