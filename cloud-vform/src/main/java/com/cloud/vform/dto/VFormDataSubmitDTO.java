package com.cloud.vform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * 提交表单数据DTO
 */
@Data
public class VFormDataSubmitDTO {
    
    /**
     * 表单模板ID
     */
    @NotNull(message = "表单模板ID不能为空")
    private Long templateId;
    
    /**
     * 表单编码
     */
    @NotBlank(message = "表单编码不能为空")
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
    private Map<String,Object> formData;
    
    /**
     * 表单状态（0-草稿，1-已提交）
     */
    @NotNull(message = "表单状态不能为空")
    private Integer formStatus;
    
}