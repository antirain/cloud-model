package com.cloud.vform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.vform.dto.VFormTemplateCreateDTO;
import com.cloud.vform.dto.VFormTemplateUpdateDTO;
import com.cloud.vform.entity.VFormTemplate;
import com.cloud.vform.service.VFormTemplateService;
import com.cloud.vform.vo.VFormTemplateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * VForm模板控制器
 */
@Tag(name = "VForm模板管理")
@RestController
@RequestMapping("/vform/template")
@RequiredArgsConstructor
@Validated
public class VFormTemplateController {
    
    private final VFormTemplateService vFormTemplateService;
    
    @Operation(summary = "获取表单模板列表")
    @GetMapping("/list")
    public List<VFormTemplateVO> list() {
        List<VFormTemplate> templates = vFormTemplateService.getEnabledTemplates();
        return templates.stream().map(this::convertToVO).collect(Collectors.toList());
    }
    
    @Operation(summary = "分页查询表单模板")
    @GetMapping("/page")
    public Page<VFormTemplateVO> page(@RequestParam(defaultValue = "1") Integer current,
                                    @RequestParam(defaultValue = "10") Integer size) {
        Page<VFormTemplate> page = new Page<>(current, size);
        Page<VFormTemplate> result = vFormTemplateService.page(page);
        
        Page<VFormTemplateVO> voPage = new Page<>();
        BeanUtils.copyProperties(result, voPage);
        voPage.setRecords(result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList()));
        return voPage;
    }
    
    @Operation(summary = "根据ID获取表单模板")
    @GetMapping("/{id}")
    public VFormTemplateVO getById(@PathVariable Long id) {
        VFormTemplate template = vFormTemplateService.getById(id);
        return convertToVO(template);
    }
    
    @Operation(summary = "根据编码获取表单模板")
    @GetMapping("/code/{formCode}")
    public VFormTemplateVO getByCode(@PathVariable String formCode) {
        VFormTemplate template = vFormTemplateService.getByFormCode(formCode);
        return convertToVO(template);
    }
    
    @Operation(summary = "创建表单模板")
    @PostMapping
    public Boolean create(@Valid @RequestBody VFormTemplateCreateDTO createDTO) {
        VFormTemplate template = new VFormTemplate();
        BeanUtils.copyProperties(createDTO, template);
        return vFormTemplateService.createTemplate(template);
    }
    
    @Operation(summary = "更新表单模板")
    @PutMapping
    public Boolean update(@Valid @RequestBody VFormTemplateUpdateDTO updateDTO) {
        VFormTemplate template = new VFormTemplate();
        BeanUtils.copyProperties(updateDTO, template);
        return vFormTemplateService.updateTemplate(template);
    }
    
    @Operation(summary = "删除表单模板")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return vFormTemplateService.deleteTemplate(id);
    }
    
    @Operation(summary = "启用/禁用表单模板")
    @PutMapping("/status/{id}")
    public Boolean toggleStatus(@PathVariable Long id, @RequestParam Integer status) {
        return vFormTemplateService.toggleTemplateStatus(id, status);
    }
    
    private VFormTemplateVO convertToVO(VFormTemplate template) {
        if (template == null) {
            return null;
        }
        VFormTemplateVO vo = new VFormTemplateVO();
        BeanUtils.copyProperties(template, vo);
        return vo;
    }
}