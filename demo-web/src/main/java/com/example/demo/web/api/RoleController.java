package com.example.demo.web.api;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.RoleQueryRequest;
import com.example.demo.api.request.RoleSaveRequest;
import com.example.demo.api.response.RoleVO;
import com.example.demo.service.RoleService;
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
@RequestMapping("/demo/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public RestResult listUsers(RoleQueryRequest queryRequest, PageRequest pageRequest) {
        PageResult<RoleVO> result = roleService.listRoles(queryRequest, pageRequest);
        return RestResult.success(result);
    }

    @GetMapping("/get")
    public RestResult getUser(IdentifierRequest request) {
        RoleVO result = roleService.getRole(request);
        return RestResult.success(result);
    }

    @PostMapping("/add")
    public RestResult addUser(@RequestBody RoleSaveRequest request) {
        int count = roleService.saveRole(request);
        return RestResult.success(count);
    }

    @PostMapping("/edit")
    public RestResult editUser(@RequestBody RoleSaveRequest request) {
        int count = roleService.saveRole(request);
        return RestResult.success(count);
    }

    @PostMapping("/remove")
    public RestResult removeUser(@RequestBody IdentifierRequest request) {
        int count = roleService.removeRole(request);
        return RestResult.success(count);
    }

}