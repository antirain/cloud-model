package com.cloud.product.controller;

import com.cloud.product.entity.Category;
import com.cloud.product.service.CategoryService;
import com.cloud.common.web.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author local
 * @date 2025-09-29
 * @description 商品分类控制器
 */
@RestController
@RequestMapping("/category")
@Tag(name = "商品分类服务", description = "商品分类相关接口")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 获取分类详情
     */
    @GetMapping("/detail/{categoryId}")
    @Operation(summary = "获取分类详情", description = "根据分类ID获取分类详情")
    public Result<Category> getCategoryById(@PathVariable("categoryId") String categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return Result.success(category);
    }

    /**
     * 获取分类树
     */
    @GetMapping("/tree")
    @Operation(summary = "获取分类树", description = "获取完整的商品分类树结构")
    public Result<List<Category>> getCategoryTree() {
        List<Category> categoryTree = categoryService.getCategoryTree();
        return Result.success(categoryTree);
    }

    /**
     * 获取子分类
     */
    @GetMapping("/sub/{parentId}")
    @Operation(summary = "获取子分类", description = "根据父分类ID获取子分类列表")
    public Result<List<Category>> getSubCategories(@PathVariable("parentId") String parentId) {
        List<Category> subCategories = categoryService.getSubCategories(parentId);
        return Result.success(subCategories);
    }

    /**
     * 获取分类路径
     */
    @GetMapping("/path/{categoryId}")
    @Operation(summary = "获取分类路径", description = "获取从顶级分类到指定分类的完整路径")
    public Result<List<Category>> getCategoryPath(@PathVariable("categoryId") String categoryId) {
        List<Category> categoryPath = categoryService.getCategoryPath(categoryId);
        return Result.success(categoryPath);
    }
}