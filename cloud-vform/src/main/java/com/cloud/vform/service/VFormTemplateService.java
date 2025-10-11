package com.cloud.vform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.vform.entity.VFormTemplate;

import java.util.List;

/**
 * VForm模板服务接口
 */
public interface VFormTemplateService extends IService<VFormTemplate> {
    
    /**
     * 根据表单编码获取表单模板
     */
    VFormTemplate getByFormCode(String formCode);
    
    /**
     * 获取所有启用的表单模板
     */
    List<VFormTemplate> getEnabledTemplates();
    
    /**
     * 创建表单模板
     */
    boolean createTemplate(VFormTemplate template);
    
    /**
     * 更新表单模板
     */
    boolean updateTemplate(VFormTemplate template);
    
    /**
     * 删除表单模板（逻辑删除）
     */
    boolean deleteTemplate(Long id);
    
    /**
     * 启用/禁用表单模板
     */
    boolean toggleTemplateStatus(Long id, Integer status);
}