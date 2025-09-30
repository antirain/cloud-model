package com.cloud.system.mapper;

import com.cloud.system.entity.SysRole;
import com.cloud.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysRole> selectRolesByUserId(Long userId);

    List<String> selectPermissionByRoleIds(@Param("roleIds") List<Long> roleIds);
}
