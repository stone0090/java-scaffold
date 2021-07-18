package com.example.demo.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDeleteRequest implements Serializable {

    @NotNull(message = "用户id不能为空")
    private Integer id;

}
