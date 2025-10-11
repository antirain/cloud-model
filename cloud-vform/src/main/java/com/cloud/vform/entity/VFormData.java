package com.cloud.vform.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.cloud.common.datasource.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * VForm3 表单数据实体类 - 存储用户填写的表单数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "form_data", autoResultMap = true)
public class VFormData extends BaseEntity {

    /**
     * 表单模板ID
     */
    @TableField("template_id")
    private Long templateId;
    
    /**
     * 表单编码
     */
    @TableField("form_code")
    private String formCode;
    
    /**
     * 业务ID（关联具体业务数据）
     */
    @TableField("business_id")
    private String businessId;
    
    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;
    
    /**
     * 表单数据JSON
     */
    @TableField(value = "form_data" , typeHandler = JacksonTypeHandler.class)
    private Map<String,Object> formData;
    
    /**
     * 表单状态（0-草稿，1-已提交，2-已审核，3-已驳回）
     */
    @TableField("form_status")
    private Integer formStatus;
    
    /**
     * 提交人ID
     */
    @TableField("submit_user_id")
    private Long submitUserId;
    
    /**
     * 提交人姓名
     */
    @TableField("submit_user_name")
    private String submitUserName;
    
    /**
     * 提交时间
     */
    @TableField("submit_time")
    private LocalDateTime submitTime;
    
    /**
     * 审核人ID
     */
    @TableField("review_user_id")
    private Long reviewUserId;
    
    /**
     * 审核人姓名
     */
    @TableField("review_user_name")
    private String reviewUserName;
    
    /**
     * 审核时间
     */
    @TableField("review_time")
    private LocalDateTime reviewTime;
    
    /**
     * 审核意见
     */
    @TableField("review_comment")
    private String reviewComment;
    
}