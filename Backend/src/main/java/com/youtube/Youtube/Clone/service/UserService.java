package com.youtube.Youtube.Clone.service;


import com.youtube.Youtube.Clone.model.User;
import com.youtube.Youtube.Clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser(){

        return userRepository.findBySub("").orElseThrow(() -> new IllegalArgumentException("cannot find user with sub"));
    }

    public void addToLikedVideos(String videoId) {

        User user = getCurrentUser();
        user.addToLikeVideos(videoId);
        userRepository.save(user);
    }

    public boolean ifLikedVideo(String videoId){
        return getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public boolean ifDisLikedVideo(String videoId) {
        return getCurrentUser().getDislikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public void removeFromLikedVideos(String videoId) {

        User user = getCurrentUser();
        user.removeFromLikeVideos(videoId);
        userRepository.save(user);
    }

    public void removeFromDislikedVideos(String videoId) {
        User user = getCurrentUser();
        user.removeFromDislikeVideos(videoId);
        userRepository.save(user);
    }

    public void addToDislikedVideos(String videoId) {
        User user = getCurrentUser();
        user.addToDislikeVideos(videoId);
        userRepository.save(user);
    }

    public void addVideoToHistory(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToVideoHistory(videoId);
        userRepository.save(currentUser);
    }

    public Set<String> userHistory(String userId) {
        User user = getUserById(userId);
        return user.getVideoHistory();
    }

    private User getUserById(String userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find user"));
    }
}
