package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.UserQueryRequest;
import com.example.demo.api.request.UserSaveRequest;
import com.example.demo.api.response.PermissionVO;
import com.example.demo.api.response.RoleVO;
import com.example.demo.api.response.UserBriefVO;
import com.example.demo.api.response.UserDetailVO;
import com.example.demo.dao.mybatis.entity.PermissionDO;
import com.example.demo.dao.mybatis.entity.PermissionDOExample;
import com.example.demo.dao.mybatis.entity.RoleDO;
import com.example.demo.dao.mybatis.entity.RoleDOExample;
import com.example.demo.dao.mybatis.entity.RolePermissionRelationDO;
import com.example.demo.dao.mybatis.entity.RolePermissionRelationDOExample;
import com.example.demo.dao.mybatis.entity.UserDO;
import com.example.demo.dao.mybatis.entity.UserDOExample;
import com.example.demo.dao.mybatis.entity.UserDOExample.Criteria;
import com.example.demo.dao.mybatis.entity.UserRoleRelationDO;
import com.example.demo.dao.mybatis.entity.UserRoleRelationDOExample;
import com.example.demo.dao.mybatis.mapper.PermissionDOMapper;
import com.example.demo.dao.mybatis.mapper.RoleDOMapper;
import com.example.demo.dao.mybatis.mapper.RolePermissionRelationDOMapper;
import com.example.demo.dao.mybatis.mapper.UserDOMapper;
import com.example.demo.dao.mybatis.mapper.UserRoleRelationDOMapper;
import com.example.demo.service.converter.UserConverter;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private RoleDOMapper roleDOMapper;

    @Autowired
    private PermissionDOMapper permissionDOMapper;

    @Autowired
    private UserRoleRelationDOMapper userRoleRelationDOMapper;

    @Autowired
    private RolePermissionRelationDOMapper rolePermissionRelationDOMapper;

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
        pageResult.setList(result.stream().map(UserConverter::toBriefVO).collect(Collectors.toList()));
        return pageResult;
    }

    @Override
    public UserDetailVO getUser(IdentifierRequest request) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andIdEqualTo(request.getId());
        List<UserDO> result = userDOMapper.selectByExample(example);
        return CollectionUtils.isEmpty(result) ? null : UserConverter.toDetailVO(result.get(0));
    }

    @Override
    public UserDetailVO getUserWithRoleAndPermission(String username) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andUsernameEqualTo(username);
        List<UserDO> result = userDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        UserDetailVO userDetailVO = UserConverter.toDetailVO(result.get(0));
        userDetailVO.setRoles(listRoles(userDetailVO.getUsername()));
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

    private List<RoleVO> listRoles(String username) {
        UserRoleRelationDOExample example = new UserRoleRelationDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andUsernameEqualTo(username);
        List<UserRoleRelationDO> userRoleRelationDOList = userRoleRelationDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userRoleRelationDOList)) {
            return Collections.emptyList();
        }
        List<String> roleCodeList = userRoleRelationDOList.stream().map(UserRoleRelationDO::getRoleCode)
            .collect(Collectors.toList());
        RoleDOExample roleDOExample = new RoleDOExample();
        roleDOExample.createCriteria().andIsDeletedEqualTo(0).andRoleCodeIn(roleCodeList);
        List<RoleDO> roleDOList = roleDOMapper.selectByExample(roleDOExample);
        if (CollectionUtils.isEmpty(roleDOList)) {
            return Collections.emptyList();
        }
        Map<String, RoleDO> roleDOMap = roleDOList.stream()
            .collect(Collectors.toMap(RoleDO::getRoleCode, Function.identity(), (x, y) -> y));
        Map<String, List<PermissionVO>> roleCodePermissionVOMap = listPermissions(roleCodeList);
        List<RoleVO> roleVOList = new ArrayList<>();
        userRoleRelationDOList.forEach(userRoleRelationDO -> {
            RoleDO roleDO = roleDOMap.get(userRoleRelationDO.getRoleCode());
            RoleVO roleVO = UserConverter.toRoleVO(roleDO);
            if (MapUtils.isNotEmpty(roleCodePermissionVOMap)) {
                roleVO.setPermissions(roleCodePermissionVOMap.get(userRoleRelationDO.getRoleCode()));
            }
            roleVOList.add(roleVO);
        });
        return roleVOList;
    }

    private Map<String, List<PermissionVO>> listPermissions(List<String> roleCodeList) {
        RolePermissionRelationDOExample example = new RolePermissionRelationDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andRoleCodeIn(roleCodeList);
        List<RolePermissionRelationDO> relationDOList = rolePermissionRelationDOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(relationDOList)) {
            return Collections.emptyMap();
        }
        List<String> permissionCodeList = relationDOList.stream().map(RolePermissionRelationDO::getPermissionCode)
            .collect(Collectors.toList());
        PermissionDOExample permissionDOExample = new PermissionDOExample();
        permissionDOExample.createCriteria().andIsDeletedEqualTo(0).andPermissionCodeIn(permissionCodeList);
        List<PermissionDO> permissionDOList = permissionDOMapper.selectByExample(permissionDOExample);
        if (CollectionUtils.isEmpty(permissionDOList)) {
            return Collections.emptyMap();
        }
        Map<String, PermissionDO> permissionDOMap = permissionDOList.stream()
            .collect(Collectors.toMap(PermissionDO::getPermissionCode, Function.identity(), (x, y) -> x));
        Map<String, List<PermissionVO>> roleCodePermissionVOMap = relationDOList.stream()
            .collect(Collectors.groupingBy(RolePermissionRelationDO::getRoleCode, Collectors.mapping(
                x -> UserConverter.toPermissionVO(permissionDOMap.get(x.getPermissionCode())), Collectors.toList())));
        return roleCodePermissionVOMap;
    }

}
