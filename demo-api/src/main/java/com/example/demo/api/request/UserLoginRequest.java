package com.example.demo.api.request;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserLoginRequest implements Serializable {

    @NotNull(message = "账号不能为空")
    @Size(min = 3, max = 10, message = "账号必须介于3到10位字符之间")
    @Pattern(regexp = "^\\w+$", message = "账号只能包含英文字母、数字、下划线")
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 3, max = 10, message = "密码必须介于3到10位字符之间")
    @Pattern(regexp = "^\\w+$", message = "密码只能包含英文字母、数字、下划线")
    private String password;

    private String type;

    private boolean autoLogin;

}
