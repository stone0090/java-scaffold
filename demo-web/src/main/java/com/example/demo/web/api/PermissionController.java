package com.example.demo.web.api;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.PermissionQueryRequest;
import com.example.demo.api.request.PermissionSaveRequest;
import com.example.demo.api.request.UserQueryRequest;
import com.example.demo.api.request.UserSaveRequest;
import com.example.demo.api.response.PermissionVO;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.service.PermissionService;
import com.example.demo.service.UserService;
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
@RequestMapping("/demo/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    public RestResult listUsers(PermissionQueryRequest queryRequest, PageRequest pageRequest) {
        PageResult<PermissionVO> result = permissionService.listPermissions(queryRequest, pageRequest);
        return RestResult.success(result);
    }

    @GetMapping("/get")
    public RestResult getUser(IdentifierRequest request) {
        PermissionVO result = permissionService.getPermission(request);
        return RestResult.success(result);
    }

    @PostMapping("/add")
    public RestResult addUser(@RequestBody PermissionSaveRequest request) {
        int count = permissionService.savePermission(request);
        return RestResult.success(count);
    }

    @PostMapping("/edit")
    public RestResult editUser(@RequestBody PermissionSaveRequest request) {
        int count = permissionService.savePermission(request);
        return RestResult.success(count);
    }

    @PostMapping("/remove")
    public RestResult removeUser(@RequestBody IdentifierRequest request) {
        int count = permissionService.removePermission(request);
        return RestResult.success(count);
    }

}