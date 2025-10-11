package com.cloud.vform.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 表单数据VO对象
 */
@Data
public class VFormDataVO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 表单模板ID
     */
    private Long templateId;
    
    /**
     * 表单编码
     */
    private String formCode;
    
    /**
     * 业务ID（关联具体业务数据）
     */
    private String businessId;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 表单数据JSON
     */
    private String formData;
    
    /**
     * 表单状态（0-草稿，1-已提交，2-已审核，3-已驳回）
     */
    private Integer formStatus;
    
    /**
     * 提交人ID
     */
    private Long submitUserId;
    
    /**
     * 提交人姓名
     */
    private String submitUserName;
    
    /**
     * 提交时间
     */
    private LocalDateTime submitTime;
    
    /**
     * 审核人ID
     */
    private Long reviewUserId;
    
    /**
     * 审核人姓名
     */
    private String reviewUserName;
    
    /**
     * 审核时间
     */
    private LocalDateTime reviewTime;
    
    /**
     * 审核意见
     */
    private String reviewComment;
    
    /**
     * 创建人ID
     */
    private Long createUserId;
    
    /**
     * 创建人姓名
     */
    private String createUserName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新人ID
     */
    private Long updateUserId;
    
    /**
     * 更新人姓名
     */
    private String updateUserName;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}