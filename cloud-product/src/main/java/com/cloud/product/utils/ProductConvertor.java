package com.cloud.product.utils;

import com.cloud.product.entity.Product;
import com.cloud.product.entity.ProductSku;
import com.cloud.product.vo.ProductSkuVO;
import com.cloud.product.vo.ProductListItemVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author local
 * @date 2025/9/29 16:07
 * @description
 */
@Mapper(componentModel = "spring")
public interface ProductConvertor {

    ProductSkuVO toProductSkuVO(ProductSku productSku);
}
