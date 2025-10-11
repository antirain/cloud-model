package com.cloud.system.util;

import com.cloud.api.system.dto.UserCreateDTO;
import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.api.system.dto.UserUpdateDTO;
import com.cloud.system.entity.SysUser;
import com.cloud.system.vo.UserListVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
@Mapper(componentModel = "spring")
public interface UserConvertor {

//    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 将User实体转换为UserVO
     * @param sysUser 用户实体
     * @return 用户视图对象
     */
    UserListVO toUserListVO(SysUser sysUser);
    
    /**
     * 将UserVO转换为User实体
     * @param userListVO 用户视图对象
     * @return 用户实体
     */
    SysUser toUser(UserListVO userListVO);

    SysUser toUser(UserCreateDTO userCreateDTO);

    SysUser toUser(UserUpdateDTO userUpdateDTO);

    UserInfoDTO toUserInfoDTO(SysUser sysUser);

    UserLoginDTO toUserLoginDTO(SysUser sysUser);
    /**
     * 将User实体列表转换为UserVO列表
     * @param users 用户实体列表
     * @return 用户视图对象列表
     */
    List<UserListVO> toListUserVO(List<SysUser> users);

}