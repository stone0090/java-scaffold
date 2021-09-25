package com.example.demo.web.api;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.PermissionQueryRequest;
import com.example.demo.api.request.PermissionSaveRequest;
import com.example.demo.api.response.PermissionVO;
import com.example.demo.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stone
 * @date 2021/08/02
 */
@RestController
@Api(value = "PermissionController", tags = "权限管理")
@RequestMapping("/demo/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("获取权限列表")
    @GetMapping("/list")
    public RestResult listUsers(PermissionQueryRequest queryRequest, PageRequest pageRequest) {
        PageResult<PermissionVO> result = permissionService.listPermissions(queryRequest, pageRequest);
        return RestResult.success(result);
    }

    @ApiOperation("获取单个权限")
    @GetMapping("/get")
    public RestResult getUser(IdentifierRequest request) {
        PermissionVO result = permissionService.getPermission(request);
        return RestResult.success(result);
    }

    @ApiOperation("新增权限")
    @PostMapping("/add")
    public RestResult addUser(@RequestBody PermissionSaveRequest request) {
        int count = permissionService.savePermission(request);
        return RestResult.success(count);
    }

    @ApiOperation("编辑权限")
    @PostMapping("/edit")
    public RestResult editUser(@RequestBody PermissionSaveRequest request) {
        int count = permissionService.savePermission(request);
        return RestResult.success(count);
    }

    @ApiOperation("移除权限")
    @PostMapping("/remove")
    public RestResult removeUser(@RequestBody IdentifierRequest request) {
        int count = permissionService.removePermission(request);
        return RestResult.success(count);
    }

}