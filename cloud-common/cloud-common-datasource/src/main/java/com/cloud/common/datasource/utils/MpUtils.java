package com.cloud.common.datasource.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.common.datasource.entity.BaseEntity;

/**
 * @author local
 * @date 2025/9/30 10:51
 * @description
 */
public class MpUtils {
    public static <T extends BaseEntity> boolean existsById(BaseMapper<T> mapper, Long id) {
        return mapper.selectCount(Wrappers.<T>lambdaQuery().eq(BaseEntity::getId, id)) > 0;
    }
}