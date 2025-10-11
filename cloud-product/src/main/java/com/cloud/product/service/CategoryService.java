package com.cloud.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.product.entity.Category;

import java.util.List;

/**
 * 商品分类服务接口
 * 用于定义商品分类相关的业务方法
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据分类ID获取分类详情
     * @param categoryId 分类ID
     * @return 分类详情
     */
    Category getCategoryById(String categoryId);

    /**
     * 获取分类树（所有层级）
     * @return 分类树结构
     */
    List<Category> getCategoryTree();

    /**
     * 获取指定分类下的子分类
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> getSubCategories(String parentId);

    /**
     * 获取商品的完整分类路径
     * @param categoryId 分类ID
     * @return 分类路径列表（从顶级到当前分类）
     */
    List<Category> getCategoryPath(String categoryId);
}