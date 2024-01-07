package com.youtube.Youtube.Clone.controller;


import com.youtube.Youtube.Clone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {//w4f

    private final VideoService videoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file")MultipartFile file){

        videoService.uploadVideo(file);
    }
}
