package com.example.demo.service.converter;


import com.example.demo.api.request.UserSaveRequest;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.response.UserDetailVO;
import com.example.demo.dao.mybatis.entity.UserDO;

public class UserConverter {

    public static UserBriefVO toBriefVO(UserDO userDO) {
        UserBriefVO userBriefVO = new UserBriefVO();
        userBriefVO.setId(userDO.getId());
        userBriefVO.setGmtModified(userDO.getGmtModified());
        userBriefVO.setUsername(userDO.getUsername());
        userBriefVO.setNickname(userDO.getNickname());
        userBriefVO.setPassword(userDO.getPassword());
        return userBriefVO;
    }

    public static UserDetailVO toDetailVO(UserDO userDO) {
        UserDetailVO userDetailVO = new UserDetailVO();
        userDetailVO.setId(userDO.getId());
        userDetailVO.setGmtModified(userDO.getGmtModified());
        userDetailVO.setUsername(userDO.getUsername());
        userDetailVO.setNickname(userDO.getNickname());
        userDetailVO.setPassword(userDO.getPassword());
        userDetailVO.setAvatar(userDO.getAvatar());
        userDetailVO.setResume(userDO.getResume());
        userDetailVO.setPhone(userDO.getPhone());
        userDetailVO.setEmail(userDO.getEmail());
        userDetailVO.setAuthority("admin");
        userDetailVO.setLoginType("account");
        userDetailVO.setAccess("admin");
        return userDetailVO;
    }

    public static UserDO toDO(UserSaveRequest request) {
        UserDO userDO = new UserDO();
        userDO.setId(request.getId());
        userDO.setUsername(request.getUsername());
        userDO.setPassword(request.getPassword());
        userDO.setNickname(request.getNickname());
        userDO.setResume(request.getResume());
        userDO.setPhone(request.getPhone());
        userDO.setEmail(request.getEmail());
        return userDO;
    }
}
