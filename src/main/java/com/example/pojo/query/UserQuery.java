package com.example.pojo.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {
    private String message;

    private String id;

    private String nickname;

    private String birthday;

    private String signature;

    private String phoneNumber;

    private String collectione;

    private String state;

    private String headPortraitUrl;

    private String permission;

}
