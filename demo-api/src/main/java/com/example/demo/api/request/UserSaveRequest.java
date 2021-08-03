package com.example.demo.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author stone
 * @date 2021/07/18
 */
@Data
public class UserSaveRequest implements Serializable {

    private Integer id;

    @NotNull(message = "账号不能为空")
    @Size(min = 3, max = 20, message = "账号必须介于3到10位字符之间")
    @Pattern(regexp = "^\\w+$", message = "账号只能包含英文字母、数字、下划线")
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 3, max = 20, message = "密码必须介于3到10位字符之间")
    @Pattern(regexp = "^\\w+$", message = "密码只能包含英文字母、数字、下划线")
    private String password;

    @Size(max = 20, message = "昵称必须小于20位字符")
    private String nickname;

    @Size(max = 20, message = "简介必须小于20位字符")
    private String resume;

    @Size(max = 20, message = "电话必须小于20位字符")
    private String phone;

    @Size(max = 20, message = "邮件必须小于20位字符")
    private String email;

}
