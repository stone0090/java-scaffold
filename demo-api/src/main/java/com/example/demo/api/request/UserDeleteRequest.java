package com.example.demo.api.request;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

@Data
public class UserDeleteRequest implements Serializable {

    @NotNull(message = "用户id不能为空")
    private Integer id;

}
