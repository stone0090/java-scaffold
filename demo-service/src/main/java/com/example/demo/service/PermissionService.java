package com.example.demo.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.PermissionQueryRequest;
import com.example.demo.api.request.PermissionSaveRequest;
import com.example.demo.api.response.PermissionVO;
import org.springframework.validation.annotation.Validated;

/**
 * @author stone
 * @date 2021/08/03
 */
@Validated
public interface PermissionService {

    PageResult<PermissionVO> listPermissions(@NotNull(message = "查询条件不能为空") @Valid PermissionQueryRequest queryRequest,
                                             @NotNull(message = "分页参数不能为空") @Valid PageRequest pageRequest);

    PermissionVO getPermission(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);

    int savePermission(@NotNull(message = "入参不能为空") @Valid PermissionSaveRequest request);

    int removePermission(@NotNull(message = "入参不能为空") @Valid IdentifierRequest request);
}
