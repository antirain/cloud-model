package com.cloud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.system.entity.Role;
import com.cloud.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    UserLoginDTO selectByName(@Param("username") String username);

    List<Role> selectRoleCodesByUserId(@Param("userId") Long userId);

    List<String> selectPermissionByRoleIds(@Param("roleIds") List<Long> roleIds);
}