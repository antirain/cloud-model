package com.cloud.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.web.context.UserContext;
import com.cloud.product.entity.Category;
import com.cloud.product.enumration.CategoryStatusEnum;
import com.cloud.product.mapper.CategoryMapper;
import com.cloud.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author local
 * @date 2025-09-29
 * @description
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    // 模拟数据存储（实际项目中应该使用数据库）
    private static final Map<String, Category> CATEGORY_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<Category>> CATEGORY_TREE = new ConcurrentHashMap<>();

    static {
        // 初始化一些模拟数据
        Category category1 = new Category();
        category1.setId(Long.valueOf("C001"));
        category1.setCategoryName("电子产品");
        category1.setParentId(Long.valueOf("0"));
        category1.setLevel(1);
        category1.setSortOrder(1);
        category1.setStatus(CategoryStatusEnum.ENABLED.getCode());
        category1.setCreateTime(LocalDateTime.now());
        category1.setUpdateTime(LocalDateTime.now());
        CATEGORY_DATA.put("C001", category1);

        Category category2 = new Category();
        category2.setId(Long.valueOf("C002"));
        category2.setCategoryName("手机");
        category2.setParentId(Long.valueOf("C001"));
        category2.setLevel(2);
        category2.setSortOrder(1);
        category2.setStatus(CategoryStatusEnum.ENABLED.getCode());
        category2.setCreateTime(LocalDateTime.now());
        category2.setUpdateTime(LocalDateTime.now());
        CATEGORY_DATA.put("C002", category2);

        Category category3 = new Category();
        category3.setId(Long.valueOf("C003"));
        category3.setCategoryName("笔记本电脑");
        category3.setParentId(Long.valueOf("C001"));
        category3.setLevel(2);
        category3.setSortOrder(2);
        category3.setStatus(CategoryStatusEnum.ENABLED.getCode());
        category3.setCreateTime(LocalDateTime.now());
        category3.setUpdateTime(LocalDateTime.now());
        CATEGORY_DATA.put("C003", category3);

        // 构建分类树
        List<Category> level1Categories = new ArrayList<>();
        level1Categories.add(category1);
        CATEGORY_TREE.put("0", level1Categories);

        List<Category> level2Categories = new ArrayList<>();
        level2Categories.add(category2);
        level2Categories.add(category3);
        CATEGORY_TREE.put("C001", level2Categories);
    }

    @Override
    public Category getCategoryById(String categoryId) {
        // 在实际项目中，这里应该从数据库查询
        return CATEGORY_DATA.get(categoryId);
    }

    @Override
    public List<Category> getCategoryTree() {
        // 在实际项目中，这里应该从数据库查询并构建分类树
        return CATEGORY_TREE.getOrDefault("0", Collections.emptyList());
    }

    @Override
    public List<Category> getSubCategories(String parentId) {
        // 在实际项目中，这里应该从数据库查询
        return CATEGORY_TREE.getOrDefault(parentId, Collections.emptyList());
    }

    @Override
    public List<Category> getCategoryPath(String categoryId) {
        // 在实际项目中，这里应该从数据库查询并构建分类路径
        List<Category> path = new ArrayList<>();
        Category category = CATEGORY_DATA.get(categoryId);
        if (category != null) {
            // 从当前分类向上遍历，构建完整的分类路径
            while (category != null) {
                // 从头部添加，保证顺序是从顶级到当前分类
                path.add(0, category);
                Long parentId = category.getParentId();
                if ("0".equals(parentId) || parentId == null) {
                    break;
                }
                category = CATEGORY_DATA.get(parentId);
            }
        }
        return path;
    }
}