package com.cloud.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.product.dto.ProductListDTO;
import com.cloud.product.entity.Category;
import com.cloud.product.entity.Product;
import com.cloud.product.vo.ProductListItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<ProductListItemVO> getProductList(Page<ProductListItemVO> page, ProductListDTO dto);
}
