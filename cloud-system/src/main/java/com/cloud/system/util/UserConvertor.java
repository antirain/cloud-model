package com.cloud.system.util;

import com.cloud.api.system.dto.UserInfoDTO;
import com.cloud.api.system.dto.UserLoginDTO;
import com.cloud.api.system.dto.UserUpdateDTO;
import com.cloud.system.entity.User;
import com.cloud.system.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户对象转换工具类
 * 用于在User实体对象和UserVO视图对象之间进行转换
 */
@Mapper
public interface UserConvertor {
    
    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);
    
    /**
     * 将User实体转换为UserVO
     * @param user 用户实体
     * @return 用户视图对象
     */
    UserVO toUserVO(User user);
    
    /**
     * 将UserVO转换为User实体
     * @param userVO 用户视图对象
     * @return 用户实体
     */
    User toUser(UserVO userVO);

    User toUser(UserUpdateDTO userUpdateDTO);

    UserInfoDTO toUserInfoDTO(User user);
    /**
     * 将User实体列表转换为UserVO列表
     * @param users 用户实体列表
     * @return 用户视图对象列表
     */
    List<UserVO> toUserVOList(List<User> users);

}