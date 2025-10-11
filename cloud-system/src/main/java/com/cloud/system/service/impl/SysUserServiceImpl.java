package com.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.common.core.enums.ResultCode;
import com.cloud.common.core.exception.BusinessException;
import com.cloud.system.entity.SysRole;
import com.cloud.system.entity.SysUser;
import com.cloud.system.exception.UserErrorCode;
import com.cloud.system.mapper.SysUserMapper;
import com.cloud.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.system.util.UserConvertor;
import com.cloud.system.vo.UserDetailVO;
import com.cloud.system.vo.UserListVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author local
 * @since 2025-09-30
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final UserConvertor userConvertor;

    @Override
    public IPage<UserListVO> page(Integer pageNo, Integer pageSize, String username, Integer status) {
        Page<SysUser> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        if (username != null && !username.trim().isEmpty()) {
            wrapper.like(SysUser::getUsername, username);
        }
        if (status != null) {
            wrapper.eq(SysUser::getStatus, status);
        }

        wrapper.orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> userPage = baseMapper.selectPage(page, wrapper);

        // 将User实体列表转换为UserVO列表
        return userPage.convert(userConvertor::toUserListVO);

    }

    @Override
    public void save(UserCreateDTO dto) {
        SysUser sysUser = userConvertor.toUser(dto);
        int i = baseMapper.insert(sysUser);
        if (i == 0) {
            throw new BusinessException(UserErrorCode.INSERT_FAIL);
        }
    }

    @Override
    public void update(Long id, UserCreateDTO dto) {
        SysUser sysUser = baseMapper.selectById(id);
        Assert.isNull(sysUser, ResultCode.DATA_NOT_FOUND.getMessage());
        SysUser updateUser = userConvertor.toUser(dto);
        int i = baseMapper.updateById(updateUser);
        if (i == 0) {
            throw new BusinessException(UserErrorCode.UPDATE_FAIL);
        }
    }

    @Override
    public void delete(Long id) {
        int i = baseMapper.deleteById(id);
        if (i == 0) {
            throw new BusinessException(UserErrorCode.DELETE_FAIL);
        }
    }

    @Override
    public UserDetailVO detail(Long id) {
        return null;
    }

    @Override
    public UserLoginDTO loadUserByUsername(String username) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
        if (sysUser == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        UserLoginDTO user = userConvertor.toUserLoginDTO(sysUser);

        List<SysRole> roles = baseMapper.selectRolesByUserId(user.getId());
        List<String> roleCodes = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        if (roles != null && !roles.isEmpty()) {
            roleCodes = roles.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
            idList = roles.stream().map(SysRole::getId).collect(Collectors.toList());
        }

        List<String> menuList = new ArrayList<>();
        if (!idList.isEmpty()) {
            menuList = baseMapper.selectPermissionByRoleIds(idList);
        }

        user.setRoles(roleCodes);
        user.setPermissions(menuList);

        return user;
    }
}
