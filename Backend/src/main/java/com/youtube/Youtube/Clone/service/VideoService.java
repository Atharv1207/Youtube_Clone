package com.youtube.Youtube.Clone.service;

import com.youtube.Youtube.Clone.dto.CommentDto;
import com.youtube.Youtube.Clone.dto.UploadVideoResponse;
import com.youtube.Youtube.Clone.dto.VideoDto;
import com.youtube.Youtube.Clone.model.Comment;
import com.youtube.Youtube.Clone.model.Video;
import com.youtube.Youtube.Clone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        increaseVideoCount(savedVideo);
        userService.addVideoToHistory(videoId);

        return mapToVideoDto(savedVideo);
    }

    private VideoDto mapToVideoDto(Video videoById){

        VideoDto videoDto = new VideoDto();
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setTags(videoById.getTags());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setVideoStatus(videoById.getVideoStatus());
        videoDto.setId(videoById.getId());
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setLikedCount(videoById.getLikes().get());
        videoDto.setDislikedCount(videoById.getDislikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        return videoDto;
    }
    private void increaseVideoCount(Video savedVideo){
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
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

        videoById.incrementLikes();
        userService.addToLikedVideos(videoId);
        return mapToVideoDto(videoById);
    }

    public VideoDto dislikeVideo(String videoId) {

        Video videoById = getVideoById(videoId);

        if(userService.ifDisLikedVideo(videoId)){
            videoById.decrementDislikes();
            userService.removeFromDislikedVideos(videoId);
        }
        else if(userService.ifDisLikedVideo(videoId)){
            videoById.decrementLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        }
        else{
            videoById.incrementDislikes();
            userService.addToDislikedVideos(videoId);
        }

        videoById.incrementLikes();
        userService.addToLikedVideos(videoId);
        return mapToVideoDto(videoById);
    }

    public void addComment(String videoId, CommentDto commentDto) {
        Video video = getVideoById(videoId);
        Comment comment = new Comment();
        comment.setText(commentDto.getCommentText());
        comment.setAuthorId(commentDto.getAuthorId());
        video.addComment(comment);

        videoRepository.save(video);
    }

    public List<CommentDto> getAllComments(String videoId) {

        Video video = getVideoById(videoId);
        List<Comment> commentList = video.getCommentList();
        return commentList.stream().map(this::mapToCommentDto).toList();
    }

    private CommentDto mapToCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getText());
        commentDto.setAuthorId(comment.getAuthorId());
        return commentDto;
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream().map(this::mapToVideoDto).toList();
    }
}
