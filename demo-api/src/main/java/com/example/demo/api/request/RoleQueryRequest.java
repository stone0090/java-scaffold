package com.example.demo.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @author stone
 * @date 2021/08/03
 */
@Data
public class RoleQueryRequest implements Serializable {
    /**
     * 角色编码模糊查询
     */
    private String roleCode;
    /**
     * 角色名称模糊查询
     */
    private String roleName;
}
