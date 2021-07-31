package com.example.demo.web.api;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.request.UserLoginRequest;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.service.service.UserService;
import com.example.demo.web.config.ShiroConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/demo/shiro")
public class ShiroController {

    @Autowired
    private ShiroConfig shiroConfig;

    @PostMapping("/login")
    public RestResult login(@RequestBody @Valid UserLoginRequest request) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            return RestResult.failure(e.getMessage());
        }
        return RestResult.success();
    }

    @PostMapping("/logout")
    public RestResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RestResult.success();
    }

}