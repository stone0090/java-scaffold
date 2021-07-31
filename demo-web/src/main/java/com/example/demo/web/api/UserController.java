package com.example.demo.web.api;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.request.*;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.protocal.RestResult;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.response.UserVO;
import com.example.demo.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/current")
    public RestResult current() {
        UserVO userVO = new UserVO();
        userVO.setName("stone");
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