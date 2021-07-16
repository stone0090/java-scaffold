package com.example.demo.web.api;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.request.*;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public RestResult login(@RequestBody UserLoginRequest request) {
        UserBriefVO result = userService.login(request);
        return RestResult.success(result);
    }

    @GetMapping("/list")
    public RestResult listUsers(UserQueryRequest queryRequest, PageRequest pageRequest) {
        PageResult<UserBriefVO> result = userService.listUsers(queryRequest, pageRequest);
        return RestResult.success(result);
    }

    @GetMapping("/get")
    public RestResult getUser(@RequestParam("id") Integer id) {
        UserBriefVO result = userService.getUser(id);
        return RestResult.success(result);
    }

    @PostMapping("/save")
    public RestResult saveUser(@RequestBody UserSaveRequest request) {
        int count = userService.saveUser(request);
        return RestResult.success(count);
    }

    @PostMapping("/remove")
    public RestResult removeUser(@RequestBody UserDeleteRequest request) {
        int count = userService.removeUser(request);
        return RestResult.success(count);
    }

}