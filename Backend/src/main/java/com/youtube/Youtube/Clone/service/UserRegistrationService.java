package com.youtube.Youtube.Clone.service;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.Youtube.Clone.dto.UserInfoDto;
import com.youtube.Youtube.Clone.model.User;
import com.youtube.Youtube.Clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userInfoEndpoint}")
    private String userInfoEndpoint;

    private final UserRepository userRepository;

    public void registerUser(String tokenValue){

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();
        HttpClient.newBuilder()
                .version(HttpClient.newHttpClient().version())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDto userInfoDto = objectMapper.readValue(body, UserInfoDto.class);

            User user = new User();
            user.setFirstName(userInfoDto.getName());
            user.setLastName(userInfoDto.getLastName());
            user.setFullName(userInfoDto.getName());
            user.setEmailAddress(userInfoDto.getEmail());
            user.setSub(userInfoDto.getSub());
            userRepository.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("", e);
        }
    }
}
