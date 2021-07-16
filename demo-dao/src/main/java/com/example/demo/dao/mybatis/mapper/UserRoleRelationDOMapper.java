package com.example.demo.dao.mybatis.mapper;

import com.example.demo.dao.mybatis.entity.UserRoleRelationDO;
import com.example.demo.dao.mybatis.entity.UserRoleRelationDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleRelationDOMapper {
    int countByExample(UserRoleRelationDOExample example);

    int deleteByExample(UserRoleRelationDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleRelationDO record);

    int insertSelective(UserRoleRelationDO record);

    List<UserRoleRelationDO> selectByExample(UserRoleRelationDOExample example);

    UserRoleRelationDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRoleRelationDO record, @Param("example") UserRoleRelationDOExample example);

    int updateByExample(@Param("record") UserRoleRelationDO record, @Param("example") UserRoleRelationDOExample example);

    int updateByPrimaryKeySelective(UserRoleRelationDO record);

    int updateByPrimaryKey(UserRoleRelationDO record);
}