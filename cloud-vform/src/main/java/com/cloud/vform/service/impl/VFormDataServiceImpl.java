package com.cloud.vform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.vform.dto.VFormDataSubmitDTO;
import com.cloud.vform.entity.VFormData;
import com.cloud.vform.mapper.VFormDataMapper;
import com.cloud.vform.service.VFormDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 表单数据服务实现类
 */
@Service
public class VFormDataServiceImpl extends ServiceImpl<VFormDataMapper, VFormData> implements VFormDataService {
    
    @Override
    public boolean submitFormData(VFormDataSubmitDTO submitDTO) {
        VFormData formData = new VFormData();
        formData.setTemplateId(submitDTO.getTemplateId());
        formData.setFormCode(submitDTO.getFormCode());
        formData.setBusinessId(submitDTO.getBusinessId());
        formData.setBusinessType(submitDTO.getBusinessType());
        formData.setFormData(submitDTO.getFormData());
        formData.setFormStatus(submitDTO.getFormStatus());

        // 已提交
        if (submitDTO.getFormStatus() == 1) {
            formData.setSubmitTime(LocalDateTime.now());
        }
        
        formData.setDelFlag(0);
        return save(formData);
    }
    
    @Override
    public VFormData getByBusinessId(String businessId) {
        LambdaQueryWrapper<VFormData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VFormData::getBusinessId, businessId)
                   .eq(VFormData::getDelFlag, 0);
        return getOne(queryWrapper);
    }
    
    @Override
    public List<VFormData> getByFormCode(String formCode) {
        LambdaQueryWrapper<VFormData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VFormData::getFormCode, formCode)
                   .eq(VFormData::getDelFlag, 0)
                   .orderByDesc(VFormData::getCreateTime);
        return list(queryWrapper);
    }
    
    @Override
    public List<VFormData> getByTemplateId(Long templateId) {
        LambdaQueryWrapper<VFormData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VFormData::getTemplateId, templateId)
                   .eq(VFormData::getDelFlag, 0)
                   .orderByDesc(VFormData::getCreateTime);
        return list(queryWrapper);
    }
    
    @Override
    public boolean reviewFormData(Long id, Integer status, String comment, Long reviewUserId, String reviewUserName) {
        VFormData formData = getById(id);
        if (formData == null) {
            throw new RuntimeException("表单数据不存在");
        }
        
        formData.setFormStatus(status);
        formData.setReviewUserId(reviewUserId);
        formData.setReviewUserName(reviewUserName);
        formData.setReviewComment(comment);
        formData.setReviewTime(LocalDateTime.now());
        
        return updateById(formData);
    }
    
    @Override
    public boolean saveDraft(VFormDataSubmitDTO submitDTO) {
        // 草稿状态为0
        submitDTO.setFormStatus(0);
        return submitFormData(submitDTO);
    }
    
    @Override
    public boolean deleteFormData(Long id) {
        VFormData formData = getById(id);
        if (formData == null) {
            throw new RuntimeException("表单数据不存在");
        }
        
        formData.setDelFlag(1);
        formData.setUpdateTime(LocalDateTime.now());
        return updateById(formData);
    }
}