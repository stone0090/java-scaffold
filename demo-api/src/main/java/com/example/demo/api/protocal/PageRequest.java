package com.example.demo.api.protocal;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private Integer current;
    private Integer pageSize;

    // TODO，需要给默认值

}
