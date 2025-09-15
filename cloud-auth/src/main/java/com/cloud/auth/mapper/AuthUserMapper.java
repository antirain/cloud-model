package com.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.auth.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 认证用户Mapper接口
 */
@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUser> {
}