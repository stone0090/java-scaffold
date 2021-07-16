package com.example.demo.service.service;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.request.*;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.response.UserDetailVO;

public interface UserService {

    UserDetailVO login(UserLoginRequest request);

    PageResult<UserBriefVO> listUsers(UserQueryRequest queryRequest, PageRequest pageRequest);

    UserDetailVO getUser(Integer id);

    int saveUser(UserSaveRequest request);

    int removeUser(UserDeleteRequest request);
}
