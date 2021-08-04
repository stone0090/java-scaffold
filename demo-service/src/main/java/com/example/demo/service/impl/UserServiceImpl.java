package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.UserQueryRequest;
import com.example.demo.api.request.UserSaveRequest;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.response.UserDetailVO;
import com.example.demo.dao.mybatis.entity.UserDO;
import com.example.demo.dao.mybatis.entity.UserDOExample;
import com.example.demo.dao.mybatis.entity.UserDOExample.Criteria;
import com.example.demo.dao.mybatis.mapper.UserDOMapper;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.converter.UserConverter;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private RoleService roleService;


    @Override
    public PageResult<UserBriefVO> listUsers(UserQueryRequest queryRequest, PageRequest pageRequest) {
        UserDOExample example = new UserDOExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        if (!StringUtils.isEmpty(queryRequest.getUsername())) {
            criteria.andUsernameLike("%" + queryRequest.getUsername() + "%");
        }
        if (!StringUtils.isEmpty(queryRequest.getNickname())) {
            criteria.andNicknameLike("%" + queryRequest.getNickname() + "%");
        }
        PageHelper.startPage(pageRequest.getCurrent(), pageRequest.getPageSize());
        List<UserDO> result = userDOMapper.selectByExample(example);
        PageResult<UserBriefVO> pageResult = PageResult.buildPageResult(result);
        pageResult.setList(result.stream().map(userDO -> {
            UserBriefVO userBriefVO = UserConverter.toBriefVO(userDO);
            userBriefVO.setRoles(roleService.listRolesByUsername(userDO.getUsername()));
            return userBriefVO;
        }).collect(Collectors.toList()));
        return pageResult;
    }

    @Override
    public UserDetailVO getUser(IdentifierRequest request) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andIdEqualTo(request.getId());
        List<UserDO> result = userDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        UserDetailVO userDetailVO = UserConverter.toDetailVO(result.get(0));
        userDetailVO.setRoles(roleService.listRolesByUsername(userDetailVO.getUsername()));
        return userDetailVO;
    }

    @Override
    public UserDetailVO getUser(String username) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andUsernameEqualTo(username);
        List<UserDO> result = userDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        UserDetailVO userDetailVO = UserConverter.toDetailVO(result.get(0));
        userDetailVO.setRoles(roleService.listRolesByUsername(userDetailVO.getUsername()));
        return userDetailVO;
    }

    @Override
    public int saveUser(UserSaveRequest request) {
        UserDO userDO = UserConverter.toUserDO(request);
        if (userDO.getId() == null) {
            return userDOMapper.insertSelective(userDO);
        } else {
            userDO.setUsername(null);
            userDO.setGmtModified(new Date());
            return userDOMapper.updateByPrimaryKeySelective(userDO);
        }
    }

    @Override
    public int removeUser(IdentifierRequest request) {
        UserDO userDO = new UserDO();
        userDO.setId(request.getId());
        userDO.setIsDeleted((int)System.currentTimeMillis());
        userDO.setGmtModified(new Date());
        return userDOMapper.updateByPrimaryKeySelective(userDO);
    }

}
