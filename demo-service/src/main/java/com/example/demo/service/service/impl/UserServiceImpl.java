package com.example.demo.service.service.impl;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.request.*;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.response.UserDetailVO;
import com.example.demo.dao.mybatis.entity.UserDO;
import com.example.demo.dao.mybatis.entity.UserDOExample;
import com.example.demo.dao.mybatis.mapper.UserDOMapper;
import com.example.demo.service.converter.UserConverter;
import com.example.demo.service.exception.CustomException;
import com.example.demo.service.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    public UserDetailVO login(UserLoginRequest request) {
        UserDOExample example = new UserDOExample();
        UserDOExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andUsernameEqualTo(request.getUsername());
        List<UserDO> userDOList = userDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userDOList)) {
            throw new CustomException("用户名不存在");
        }
        UserDO userDO = userDOList.get(0);
        if (!userDO.getPassword().equals(request.getPassword())) {
            throw new CustomException("密码不正确");
        }
        return UserConverter.toDetailVO(userDO);
    }

    @Override
    public PageResult<UserBriefVO> listUsers(UserQueryRequest queryRequest, PageRequest pageRequest) {
        UserDOExample example = new UserDOExample();
        UserDOExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        if (!StringUtils.isEmpty(queryRequest.getUsername())) {
            criteria.andUsernameEqualTo(queryRequest.getUsername());
        }
        PageHelper.startPage(pageRequest.getCurrent(), pageRequest.getPageSize());
        List<UserDO> userDOList = userDOMapper.selectByExample(example);
        PageResult<UserBriefVO> pageResult = PageResult.buildPageResult(userDOList);
        pageResult.setList(userDOList.stream().map(UserConverter::toBriefVO).collect(Collectors.toList()));
        return pageResult;
    }

    @Override
    public UserDetailVO getUser(Integer id) {
        UserDOExample example = new UserDOExample();
        UserDOExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        criteria.andIdEqualTo(id);
        List<UserDO> userDOList = userDOMapper.selectByExample(example);
        return CollectionUtils.isEmpty(userDOList) ? null : UserConverter.toDetailVO(userDOList.get(0));
    }

    @Override
    public int saveUser(UserSaveRequest request) {
        Date now = new Date();
        UserDO userDO = UserConverter.toDO(request);
        userDO.setGmtModified(now);
        if (userDO.getId() == null) {
            userDO.setGmtCreate(now);
            userDO.setIsDeleted(0);
            return userDOMapper.insertSelective(userDO);
        } else {
            return userDOMapper.updateByPrimaryKeySelective(userDO);
        }
    }

    @Override
    public int removeUser(UserDeleteRequest request) {
        UserDO userDO = new UserDO();
        userDO.setId(request.getId());
        userDO.setIsDeleted((int) System.currentTimeMillis());
        userDO.setGmtModified(new Date());
        return userDOMapper.updateByPrimaryKeySelective(userDO);
    }

}
