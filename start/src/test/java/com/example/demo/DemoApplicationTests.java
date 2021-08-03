package com.example.demo;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        //userService.saveUser(null);
        //UserSaveRequest request = new UserSaveRequest();
        //request.setUsername("@@@");
        //userService.saveUser(request);
        userService.getUserWithRoleAndPermission("stone");
    }

}
