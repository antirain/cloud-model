package com.cloud.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.system.vo.UserDetailVO;
import com.cloud.system.vo.UserListVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
public interface SysUserService extends IService<SysUser> {


    IPage<UserListVO> page(Integer pageNo, Integer pageSize, String username , Integer status);

    void save(UserCreateDTO dto);

    void update(Long id, UserCreateDTO dto);

    void delete(Long id);

    UserDetailVO detail(Long id);

    UserLoginDTO loadUserByUsername(String username);
}
