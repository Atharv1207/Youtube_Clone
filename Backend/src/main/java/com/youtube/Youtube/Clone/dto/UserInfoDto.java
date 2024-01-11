package com.youtube.Youtube.Clone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String id;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String picture;
    private String email;
}
