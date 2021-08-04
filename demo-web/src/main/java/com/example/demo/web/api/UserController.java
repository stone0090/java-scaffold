package com.example.demo.web.api;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.UserQueryRequest;
import com.example.demo.api.request.UserSaveRequest;
import com.example.demo.api.response.UserBriefVO;
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
@RequestMapping("/demo/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public RestResult listUsers(UserQueryRequest queryRequest, PageRequest pageRequest) {
        PageResult<UserBriefVO> result = userService.listUsers(queryRequest, pageRequest);
        return RestResult.success(result);
    }

    @GetMapping("/get")
    public RestResult getUser(IdentifierRequest request) {
        UserBriefVO result = userService.getUser(request);
        return RestResult.success(result);
    }

    @PostMapping("/add")
    public RestResult addUser(@RequestBody UserSaveRequest request) {
        int count = userService.saveUser(request);
        return RestResult.success(count);
    }

    @PostMapping("/edit")
    public RestResult editUser(@RequestBody UserSaveRequest request) {
        int count = userService.saveUser(request);
        return RestResult.success(count);
    }

    @PostMapping("/remove")
    public RestResult removeUser(@RequestBody IdentifierRequest request) {
        int count = userService.removeUser(request);
        return RestResult.success(count);
    }

}