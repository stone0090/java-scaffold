package com.example.demo.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserBriefVO implements Serializable {

    private Integer id;

    private Date gmtModified;

    private String username;

    private String nickname;

    private String password;

}