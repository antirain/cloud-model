package com.cloud.vform.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.cloud.common.datasource.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * VForm3 表单模板实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "form_template", autoResultMap = true)
public class VFormTemplate extends BaseEntity {

    /**
     * 表单名称
     */
    @TableField("form_name")
    private String formName;

    /**
     * 表单编码（唯一标识）
     */
    @TableField("form_code")
    private String formCode;

    /**
     * 表单描述
     */
    @TableField("form_desc")
    private String formDesc;

    /**
     * 表单类型（1-系统表单，2-自定义表单）
     */
    @TableField("form_type")
    private Integer formType;

    /**
     * 表单JSON配置
     */
    @TableField(value = "form_json", typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> formJson;

    /**
     * 表单版本
     */
    @TableField("version")
    private Integer version;

    /**
     * 状态（0-禁用，1-启用）
     */
    @TableField("status")
    private Integer status;

}