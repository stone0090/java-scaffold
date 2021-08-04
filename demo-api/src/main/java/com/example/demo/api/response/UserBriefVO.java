package com.example.demo.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author stone
 * @date 2021/07/26
 */
@Data
public class UserBriefVO implements Serializable {
    private Integer id;
    private Date gmtModified;
    private String username;
    private String nickname;
    private String password;
    private String salt;
    /**
     * 用户可以有多个角色
     */
    private List<RoleVO> roles;
}