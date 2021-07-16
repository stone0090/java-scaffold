package com.example.demo.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDetailVO extends UserBriefVO {

    private String avatar;

    private String resume;

    private String phone;

    private String email;

    private String authority;

    private String loginType;

    private String access;

}