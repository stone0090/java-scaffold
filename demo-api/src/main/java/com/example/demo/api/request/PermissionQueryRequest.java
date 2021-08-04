package com.example.demo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author stone
 * @date 2021/08/03
 */
@Data
public class PermissionQueryRequest implements Serializable {
    /**
     * 权限编码模糊查询
     */
    private String permissionCode;
    /**
     * 权限名称模糊查询
     */
    private String permissionName;
    /**
     * 权限url模糊查询
     */
    private String permissionUrl;
}
