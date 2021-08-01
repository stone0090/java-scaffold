package com.example.demo.api.response;

import java.util.List;

import lombok.Data;

/**
 * @author stone
 * @date 2021/07/26
 */
@Data
public class UserDetailVO extends UserBriefVO {

    private String avatar;

    private String resume;

    private String phone;

    private String email;

    /**
     * 用户可以有多个角色
     */
    private List<RoleVO> roles;

}