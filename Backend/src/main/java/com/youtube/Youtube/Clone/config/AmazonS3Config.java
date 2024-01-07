package com.youtube.Youtube.Clone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.regions.Regions;

@Configuration
public class AmazonS3Config {

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
        amazonS3Client.withRegion(Regions.fromName(awsRegion));

        return amazonS3Client;
    }
}
