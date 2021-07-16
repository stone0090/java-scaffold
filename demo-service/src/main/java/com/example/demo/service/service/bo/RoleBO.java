package com.example.demo.service.service.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class RoleBO {
    private String id;
    private String roleCode;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<PermissionBO> permissions;
}
