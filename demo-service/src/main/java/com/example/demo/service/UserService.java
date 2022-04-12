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

    UserDetailVO getUser(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);

    UserDetailVO getUser(@NotNull(message = "入参不能为空") String username);

    /**
     * @return 返回主键id
     */
    int addUser(@NotNull(message = "入参不能为空") @Valid UserSaveRequest request);

    /**
     * @return 返回操作记录数
     */
    int editUser(@NotNull(message = "入参不能为空") @Valid UserSaveRequest request);

    /**
     * @return 返回操作记录数
     */
    int removeUser(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);
}
