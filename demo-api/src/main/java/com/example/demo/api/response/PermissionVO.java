package com.example.demo.api.response;

import java.util.Date;

import lombok.Data;

/**
 * @author stone
 * @date 2021/07/26
 */
@Data
public class PermissionVO {
    private Integer id;
    private Date gmtModified;
    private String permissionCode;
    private String permissionName;
    private String permissionUrl;
}
