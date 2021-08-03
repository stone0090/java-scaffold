package com.example.demo.api.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @author stone
 * @date 2021/08/03
 */
@Data
public class RoleQueryRequest implements Serializable {

    private String roleCode;

    private String roleName;

}
