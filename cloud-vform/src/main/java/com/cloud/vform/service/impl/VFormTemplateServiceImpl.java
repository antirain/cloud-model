package com.cloud.vform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.vform.entity.VFormTemplate;
import com.cloud.vform.mapper.VFormTemplateMapper;
import com.cloud.vform.service.VFormTemplateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * VForm模板服务实现类
 */
@Service
public class VFormTemplateServiceImpl extends ServiceImpl<VFormTemplateMapper, VFormTemplate> implements VFormTemplateService {
    
    @Override
    public VFormTemplate getByFormCode(String formCode) {
        LambdaQueryWrapper<VFormTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VFormTemplate::getFormCode, formCode)
                   .eq(VFormTemplate::getDelFlag, 0);
        return getOne(queryWrapper);
    }
    
    @Override
    public List<VFormTemplate> getEnabledTemplates() {
        LambdaQueryWrapper<VFormTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VFormTemplate::getStatus, 1)
                   .eq(VFormTemplate::getDelFlag, 0)
                   .orderByDesc(VFormTemplate::getCreateTime);
        return list(queryWrapper);
    }
    
    @Override
    public boolean createTemplate(VFormTemplate template) {
        // 检查表单编码是否已存在
        VFormTemplate existing = getByFormCode(template.getFormCode());
        if (existing != null) {
            throw new RuntimeException("表单编码已存在");
        }
        
        template.setVersion(1);
        template.setStatus(1);
        template.setDelFlag(0);
        return save(template);
    }
    
    @Override
    public boolean updateTemplate(VFormTemplate template) {
        VFormTemplate existing = getById(template.getId());
        if (existing == null) {
            throw new RuntimeException("表单模板不存在");
        }
        
        // 如果表单编码有变化，检查是否重复
        if (!existing.getFormCode().equals(template.getFormCode())) {
            VFormTemplate codeExisting = getByFormCode(template.getFormCode());
            if (codeExisting != null && !codeExisting.getId().equals(template.getId())) {
                throw new RuntimeException("表单编码已存在");
            }
        }
        
        template.setVersion(existing.getVersion() + 1);
        return updateById(template);
    }
    
    @Override
    public boolean deleteTemplate(Long id) {
        VFormTemplate template = getById(id);
        if (template == null) {
            throw new RuntimeException("表单模板不存在");
        }
        
        template.setDelFlag(1);
        template.setUpdateTime(LocalDateTime.now());
        return updateById(template);
    }
    
    @Override
    public boolean toggleTemplateStatus(Long id, Integer status) {
        VFormTemplate template = getById(id);
        if (template == null) {
            throw new RuntimeException("表单模板不存在");
        }
        
        template.setStatus(status);
        template.setUpdateTime(LocalDateTime.now());
        return updateById(template);
    }
}