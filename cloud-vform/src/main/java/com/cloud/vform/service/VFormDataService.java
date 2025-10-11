package com.cloud.vform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.vform.entity.VFormData;
import com.cloud.vform.dto.VFormDataSubmitDTO;

import java.util.List;

/**
 * 表单数据服务接口
 */
public interface VFormDataService extends IService<VFormData> {
    
    /**
     * 提交表单数据
     */
    boolean submitFormData(VFormDataSubmitDTO submitDTO);
    
    /**
     * 根据业务ID获取表单数据
     */
    VFormData getByBusinessId(String businessId);
    
    /**
     * 根据表单编码获取表单数据列表
     */
    List<VFormData> getByFormCode(String formCode);
    
    /**
     * 根据模板ID获取表单数据列表
     */
    List<VFormData> getByTemplateId(Long templateId);
    
    /**
     * 审核表单数据
     */
    boolean reviewFormData(Long id, Integer status, String comment, Long reviewUserId, String reviewUserName);
    
    /**
     * 保存草稿
     */
    boolean saveDraft(VFormDataSubmitDTO submitDTO);
    
    /**
     * 删除表单数据（逻辑删除）
     */
    boolean deleteFormData(Long id);
}