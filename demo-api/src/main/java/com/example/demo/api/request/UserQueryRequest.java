package com.example.demo.api.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author stone
 * @date 2021/07/18
 */
@Data
public class UserQueryRequest implements Serializable {
    /**
     * 账号模糊查询
     */
    private String username;
    /**
     * 昵称模糊查询
     */
    private String nickname;
}
