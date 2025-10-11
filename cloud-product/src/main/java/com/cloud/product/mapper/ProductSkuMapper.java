package com.cloud.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.product.entity.Category;
import com.cloud.product.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {
}
