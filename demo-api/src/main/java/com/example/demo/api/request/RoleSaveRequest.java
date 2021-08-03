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
public class RoleSaveRequest implements Serializable {

    private Integer id;

    @NotNull(message = "角色编码不能为空")
    @Size(min = 3, max = 20, message = "角色编码必须介于3到10位字符之间")
    @Pattern(regexp = "^\\w+$", message = "角色编码只能包含英文字母、数字、下划线")
    private String roleCode;

    @NotNull(message = "角色名称不能为空")
    @Size(min = 1, max = 20, message = "角色名称必须介于1到10位字符之间")
    private String roleName;

}
