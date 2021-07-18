package com.example.demo.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSaveRequest implements Serializable {

    private Integer id;

    @NotNull(message = "账号不能为空")
    @Size(min = 6, max = 10, message = "账号必须介于6到10个字符之间")
    private String username;

    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 10, message = "密码必须介于6到10个字符之间")
    private String password;

    @Size(max = 20, message = "昵称必须小于20个字符")
    private String nickname;

    @Size(max = 20, message = "简介必须小于20个字符")
    private String resume;

    @Size(max = 20, message = "电话必须小于20个字符")
    private String phone;

    @Size(max = 20, message = "邮件必须小于20个字符")
    private String email;

}
