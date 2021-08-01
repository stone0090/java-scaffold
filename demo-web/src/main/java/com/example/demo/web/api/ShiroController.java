package com.example.demo.web.api;

import javax.validation.Valid;

import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.request.UserLoginRequest;
import com.example.demo.api.response.UserDetailVO;
import com.example.demo.api.response.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stone
 * @date 2021/08/02
 */
@Validated
@RestController
@RequestMapping("/demo/shiro")
public class ShiroController {

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
        return RestResult.success();
    }

    @GetMapping("/current")
    public RestResult current() {
        Subject subject = SecurityUtils.getSubject();
        UserDetailVO userDetailVO = (UserDetailVO)subject.getPrincipal();
        UserVO userVO = new UserVO();
        userVO.setName(userDetailVO.getUsername());
        userVO.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        userVO.setUserid("001");
        userVO.setEmail("antdesign@alipay.com");
        userVO.setSignature("越简单，越幸运");
        userVO.setTitle("开发砖家");
        userVO.setGroup("阿里云xxx");
        userVO.setNotifyCount(10);
        userVO.setUnreadCount(5);
        userVO.setCountry("China");
        userVO.setAccess("admin");
        return RestResult.success(userVO);
    }

}