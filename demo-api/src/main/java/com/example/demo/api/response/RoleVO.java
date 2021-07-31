package com.example.demo.api.response;

import java.util.List;

import lombok.Data;

/**
 * @author stone
 * @date 2021/07/26
 */
@Data
public class RoleVO {
    private Integer id;
    private String roleCode;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private List<PermissionVO> permissions;
}
