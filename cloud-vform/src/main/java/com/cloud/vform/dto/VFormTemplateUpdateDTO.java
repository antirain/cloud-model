package com.cloud.vform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

/**
 * 更新表单模板DTO
 */
@Data
public class VFormTemplateUpdateDTO {
    
    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空")
    private Long id;
    
    /**
     * 表单名称
     */
    @NotBlank(message = "表单名称不能为空")
    private String formName;
    
    /**
     * 表单编码（唯一标识）
     */
    @NotBlank(message = "表单编码不能为空")
    private String formCode;
    
    /**
     * 表单描述
     */
    private String formDesc;
    
    /**
     * 表单类型（1-系统表单，2-自定义表单）
     */
    @NotNull(message = "表单类型不能为空")
    private Integer formType;
    
    /**
     * 表单JSON配置
     */
    private Map<String,Object> formJson;

}