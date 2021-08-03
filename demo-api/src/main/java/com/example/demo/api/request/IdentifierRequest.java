package com.example.demo.api.request;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author stone
 * @date 2021/07/18
 */
@Data
public class IdentifierRequest implements Serializable {

    @NotNull(message = "id不能为空")
    private Integer id;

}
