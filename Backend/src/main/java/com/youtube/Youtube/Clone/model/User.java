package com.youtube.Youtube.Clone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private Set<String> subscribedToUsers;
    private Set<String> subscribers;
    private String sub;
    private Set<String> videoHistory;
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> dislikedVideos = ConcurrentHashMap.newKeySet();


    public void addToLikeVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void removeFromLikeVideos(String videoId) {
        likedVideos.remove(videoId);
    }

    public void removeFromDislikeVideos(String videoId) {
        dislikedVideos.remove(videoId);
    }

    public void addToDislikeVideos(String videoId) {
        dislikedVideos.add(videoId);
    }


    public void addToVideoHistory(String videoId) {
        videoHistory.add(videoId);
    }
}
