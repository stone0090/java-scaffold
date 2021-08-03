package com.example.demo.api.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stone
 * @date 2021/07/18
 */
@Data
public class UserQueryRequest implements Serializable {

    private String username;

    private String nickname;

}
