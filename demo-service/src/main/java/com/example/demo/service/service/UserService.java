package com.example.demo.service.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.request.*;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.response.UserDetailVO;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {

    // hibernate validator 使用方法
    // https://blog.csdn.net/f641385712/article/details/97402946
    // https://www.cnblogs.com/mr-yang-localhost/p/7812038.html

    UserDetailVO login(@NotNull(message = "入参不能为空") @Valid UserLoginRequest request);

    PageResult<UserBriefVO> listUsers(@NotNull(message = "查询条件不能为空") @Valid UserQueryRequest queryRequest,
                                      @NotNull(message = "分页参数不能为空") @Valid PageRequest pageRequest);

    UserDetailVO getUser(@NotNull(message = "入参不能为空") Integer id);

    int saveUser(@NotNull(message = "入参不能为空") @Valid UserSaveRequest request);

    int removeUser(@NotNull(message = "入参不能为空") @Valid UserDeleteRequest request);
}
