package com.example.demo.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSaveRequest implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String resume;

    private String phone;

    private String email;

}
