package com.example.demo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.RoleQueryRequest;
import com.example.demo.api.request.RoleSaveRequest;
import com.example.demo.api.response.RoleVO;
import com.example.demo.dao.mybatis.entity.RoleDO;
import com.example.demo.dao.mybatis.entity.RoleDOExample;
import com.example.demo.dao.mybatis.entity.RoleDOExample.Criteria;
import com.example.demo.dao.mybatis.mapper.RoleDOMapper;
import com.example.demo.service.RoleService;
import com.example.demo.service.converter.UserConverter;
import com.github.pagehelper.PageHelper;
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
        pageResult.setList(result.stream().map(UserConverter::toRoleVO).collect(Collectors.toList()));
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
}
