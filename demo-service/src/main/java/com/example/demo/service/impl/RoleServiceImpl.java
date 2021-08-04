package com.example.demo.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.RoleQueryRequest;
import com.example.demo.api.request.RoleSaveRequest;
import com.example.demo.api.response.PermissionVO;
import com.example.demo.api.response.RoleVO;
import com.example.demo.dao.mybatis.entity.RoleDO;
import com.example.demo.dao.mybatis.entity.RoleDOExample;
import com.example.demo.dao.mybatis.entity.RoleDOExample.Criteria;
import com.example.demo.dao.mybatis.entity.UserRoleRelationDO;
import com.example.demo.dao.mybatis.entity.UserRoleRelationDOExample;
import com.example.demo.dao.mybatis.mapper.RoleDOMapper;
import com.example.demo.dao.mybatis.mapper.UserRoleRelationDOMapper;
import com.example.demo.service.PermissionService;
import com.example.demo.service.RoleService;
import com.example.demo.service.converter.UserConverter;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author stone
 * @date 2021/08/03
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDOMapper roleDOMapper;
    @Autowired
    private UserRoleRelationDOMapper userRoleRelationDOMapper;
    @Autowired
    private PermissionService permissionService;

    @Override
    public PageResult<RoleVO> listRoles(RoleQueryRequest queryRequest, PageRequest pageRequest) {
        RoleDOExample example = new RoleDOExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        if (!StringUtils.isEmpty(queryRequest.getRoleCode())) {
            criteria.andRoleCodeLike("%" + queryRequest.getRoleCode() + "%");
        }
        if (!StringUtils.isEmpty(queryRequest.getRoleName())) {
            criteria.andRoleNameLike("%" + queryRequest.getRoleName() + "%");
        }
        PageHelper.startPage(pageRequest.getCurrent(), pageRequest.getPageSize());
        List<RoleDO> result = roleDOMapper.selectByExample(example);
        PageResult<RoleVO> pageResult = PageResult.buildPageResult(result);
        pageResult.setList(listRolePermissions(result));
        return pageResult;
    }

    @Override
    public RoleVO getRole(IdentifierRequest request) {
        RoleDOExample example = new RoleDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andIdEqualTo(request.getId());
        List<RoleDO> result = roleDOMapper.selectByExample(example);
        return CollectionUtils.isEmpty(result) ? null : UserConverter.toRoleVO(result.get(0));
    }

    @Override
    public int saveRole(RoleSaveRequest request) {
        RoleDO roleDO = UserConverter.toRoleDO(request);
        if (roleDO.getId() == null) {
            return roleDOMapper.insertSelective(roleDO);
        } else {
            roleDO.setRoleCode(null);
            roleDO.setGmtModified(new Date());
            return roleDOMapper.updateByPrimaryKeySelective(roleDO);
        }
    }

    @Override
    public int removeRole(IdentifierRequest request) {
        RoleDO roleDO = new RoleDO();
        roleDO.setId(request.getId());
        roleDO.setIsDeleted((int)System.currentTimeMillis());
        roleDO.setGmtModified(new Date());
        return roleDOMapper.updateByPrimaryKeySelective(roleDO);
    }

    @Override
    public List<RoleVO> listRolesByUsername(String username) {
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
        return listRolePermissions(roleDOList);
    }

    private List<RoleVO> listRolePermissions(List<RoleDO> roleDOList) {
        List<String> roleCodeList = roleDOList.stream().map(RoleDO::getRoleCode).collect(Collectors.toList());
        Map<String, List<PermissionVO>> roleCodePermissionVOMap = permissionService.listPermissionsByRoleCodes(
            roleCodeList);
        if (MapUtils.isEmpty(roleCodePermissionVOMap)) {
            return roleDOList.stream().map(UserConverter::toRoleVO).collect(Collectors.toList());
        }
        return roleDOList.stream().map(roleDO -> {
            RoleVO roleVO = UserConverter.toRoleVO(roleDO);
            if (MapUtils.isNotEmpty(roleCodePermissionVOMap)) {
                roleVO.setPermissions(roleCodePermissionVOMap.get(roleDO.getRoleCode()));
            }
            return roleVO;
        }).collect(Collectors.toList());
    }

}
