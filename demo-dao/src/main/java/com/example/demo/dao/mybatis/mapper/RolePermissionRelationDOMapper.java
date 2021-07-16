package com.example.demo.dao.mybatis.mapper;

import com.example.demo.dao.mybatis.entity.RolePermissionRelationDO;
import com.example.demo.dao.mybatis.entity.RolePermissionRelationDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionRelationDOMapper {
    int countByExample(RolePermissionRelationDOExample example);

    int deleteByExample(RolePermissionRelationDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionRelationDO record);

    int insertSelective(RolePermissionRelationDO record);

    List<RolePermissionRelationDO> selectByExample(RolePermissionRelationDOExample example);

    RolePermissionRelationDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermissionRelationDO record, @Param("example") RolePermissionRelationDOExample example);

    int updateByExample(@Param("record") RolePermissionRelationDO record, @Param("example") RolePermissionRelationDOExample example);

    int updateByPrimaryKeySelective(RolePermissionRelationDO record);

    int updateByPrimaryKey(RolePermissionRelationDO record);
}