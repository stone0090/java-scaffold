package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.request.*;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.response.UserDetailVO;
import org.springframework.validation.annotation.Validated;

/**
 * @author stone
 * @date 2021/08/03
 */
@Validated
public interface UserService  {

    PageResult<UserBriefVO> listUsers(@NotNull(message = "查询条件不能为空") @Valid UserQueryRequest queryRequest,
                                      @NotNull(message = "分页参数不能为空") @Valid PageRequest pageRequest);

    /**
     * 获取用户详细信息(不含角色和权限)
     */
    UserDetailVO getUser(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);

    /**
     * 获取用户详细信息(包含角色和权限)
     */
    UserDetailVO getUserWithRoleAndPermission(@NotNull(message = "入参不能为空") String username);

    int saveUser(@NotNull(message = "入参不能为空") @Valid UserSaveRequest request);

    int removeUser(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);
}
