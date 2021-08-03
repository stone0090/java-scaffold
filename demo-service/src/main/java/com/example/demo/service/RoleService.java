package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.RoleQueryRequest;
import com.example.demo.api.request.RoleSaveRequest;
import com.example.demo.api.response.RoleVO;
import org.springframework.validation.annotation.Validated;

/**
 * @author stone
 * @date 2021/08/03
 */
@Validated
public interface RoleService {

    PageResult<RoleVO> listRoles(@NotNull(message = "查询条件不能为空") @Valid RoleQueryRequest queryRequest,
                                             @NotNull(message = "分页参数不能为空") @Valid PageRequest pageRequest);

    RoleVO getRole(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);

    int saveRole(@NotNull(message = "入参不能为空") @Valid RoleSaveRequest request);

    int removeRole(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);
}
