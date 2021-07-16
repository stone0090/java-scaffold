package com.example.demo.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserQueryRequest implements Serializable {

    private String username;

}
