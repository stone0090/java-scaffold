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

    private String permissionCode;

    private String permissionName;

    private String permissionUrl;

}
