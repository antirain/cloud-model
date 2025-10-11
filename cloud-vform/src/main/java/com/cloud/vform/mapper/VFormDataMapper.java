package com.cloud.vform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.vform.entity.VFormData;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表单数据Mapper接口
 */
@Mapper
public interface VFormDataMapper extends BaseMapper<VFormData> {
}