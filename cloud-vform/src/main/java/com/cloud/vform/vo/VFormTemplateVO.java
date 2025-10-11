package com.cloud.vform.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * VForm模板VO对象
 */
@Data
public class VFormTemplateVO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 表单名称
     */
    private String formName;
    
    /**
     * 表单编码（唯一标识）
     */
    private String formCode;
    
    /**
     * 表单描述
     */
    private String formDesc;
    
    /**
     * 表单类型（1-系统表单，2-自定义表单）
     */
    private Integer formType;
    
    /**
     * 表单JSON配置
     */
    private String formJson;
    
    /**
     * 表单版本
     */
    private Integer version;
    
    /**
     * 状态（0-禁用，1-启用）
     */
    private Integer status;
    
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