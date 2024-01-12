package com.youtube.Youtube.Clone.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3service implements FileService{

    private final AmazonS3Client awsS3Client;
    public static final String BUCKET_NAME = "youtubeclonebucket123";

    @Override
    public String uploadFile(MultipartFile file){
        //upload file to AWS
        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = UUID.randomUUID().toString() + "." +filenameExtension;
        var metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            awsS3Client.putObject(BUCKET_NAME, key, file.getInputStream(), metadata);
        }
        catch(IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Exception while uploading file");
        }
        awsS3Client.setObjectAcl("BUCKET_NAME", key, CannedAccessControlList.PublicRead);
        return awsS3Client.getResourceUrl("BUCKET_NAME", key);
    }
}
