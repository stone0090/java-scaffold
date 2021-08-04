package com.example.demo.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.demo.api.protocal.PageRequest;
import com.example.demo.api.protocal.PageResult;
import com.example.demo.api.request.IdentifierRequest;
import com.example.demo.api.request.PermissionQueryRequest;
import com.example.demo.api.request.PermissionSaveRequest;
import com.example.demo.api.response.PermissionVO;
import com.example.demo.dao.mybatis.entity.PermissionDO;
import com.example.demo.dao.mybatis.entity.PermissionDOExample;
import com.example.demo.dao.mybatis.entity.PermissionDOExample.Criteria;
import com.example.demo.dao.mybatis.entity.RolePermissionRelationDO;
import com.example.demo.dao.mybatis.entity.RolePermissionRelationDOExample;
import com.example.demo.dao.mybatis.mapper.PermissionDOMapper;
import com.example.demo.dao.mybatis.mapper.RolePermissionRelationDOMapper;
import com.example.demo.service.PermissionService;
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
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDOMapper permissionDOMapper;
    @Autowired
    private RolePermissionRelationDOMapper rolePermissionRelationDOMapper;

    @Override
    public PageResult<PermissionVO> listPermissions(PermissionQueryRequest queryRequest, PageRequest pageRequest) {
        PermissionDOExample example = new PermissionDOExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo(0);
        if (!StringUtils.isEmpty(queryRequest.getPermissionCode())) {
            criteria.andPermissionCodeLike("%" + queryRequest.getPermissionCode() + "%");
        }
        if (!StringUtils.isEmpty(queryRequest.getPermissionName())) {
            criteria.andPermissionNameLike("%" + queryRequest.getPermissionName() + "%");
        }
        if (!StringUtils.isEmpty(queryRequest.getPermissionUrl())) {
            criteria.andPermissionUrlLike("%" + queryRequest.getPermissionUrl() + "%");
        }
        PageHelper.startPage(pageRequest.getCurrent(), pageRequest.getPageSize());
        List<PermissionDO> result = permissionDOMapper.selectByExample(example);
        PageResult<PermissionVO> pageResult = PageResult.buildPageResult(result);
        pageResult.setList(result.stream().map(UserConverter::toPermissionVO).collect(Collectors.toList()));
        return pageResult;
    }

    @Override
    public PermissionVO getPermission(IdentifierRequest request) {
        PermissionDOExample example = new PermissionDOExample();
        example.createCriteria().andIsDeletedEqualTo(0).andIdEqualTo(request.getId());
        List<PermissionDO> result = permissionDOMapper.selectByExample(example);
        return CollectionUtils.isEmpty(result) ? null : UserConverter.toPermissionVO(result.get(0));
    }

    @Override
    public int savePermission(PermissionSaveRequest request) {
        PermissionDO permissionDO = UserConverter.toPermissionDO(request);
        if (permissionDO.getId() == null) {
            return permissionDOMapper.insertSelective(permissionDO);
        } else {
            permissionDO.setPermissionCode(null);
            permissionDO.setGmtModified(new Date());
            return permissionDOMapper.updateByPrimaryKeySelective(permissionDO);
        }
    }

    @Override
    public int removePermission(IdentifierRequest request) {
        PermissionDO permissionDO = new PermissionDO();
        permissionDO.setId(request.getId());
        permissionDO.setIsDeleted((int)System.currentTimeMillis());
        permissionDO.setGmtModified(new Date());
        return permissionDOMapper.updateByPrimaryKeySelective(permissionDO);
    }

    @Override
    public Map<String, List<PermissionVO>> listPermissionsByRoleCodes(List<String> roleCodeList) {
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
