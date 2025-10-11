package com.cloud.vform.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.vform.dto.VFormDataSubmitDTO;
import com.cloud.vform.entity.VFormData;
import com.cloud.vform.service.VFormDataService;
import com.cloud.vform.vo.VFormDataVO;
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
 * 表单数据控制器
 */
@Tag(name = "表单数据管理")
@RestController
@RequestMapping("/vform/data")
@RequiredArgsConstructor
@Validated
public class VFormDataController {

    private final VFormDataService vFormDataService;

    @Operation(summary = "提交表单数据")
    @PostMapping("/submit")
    public Boolean submit(@Valid @RequestBody VFormDataSubmitDTO submitDTO) {
        return vFormDataService.submitFormData(submitDTO);
    }

    @Operation(summary = "保存草稿")
    @PostMapping("/draft")
    public Boolean saveDraft(@Valid @RequestBody VFormDataSubmitDTO submitDTO) {
        return vFormDataService.saveDraft(submitDTO);
    }

    @Operation(summary = "根据业务ID获取表单数据")
    @GetMapping("/business/{businessId}")
    public VFormDataVO getByBusinessId(@PathVariable String businessId) {
        VFormData formData = vFormDataService.getByBusinessId(businessId);
        return convertToVO(formData);
    }

    @Operation(summary = "根据表单编码获取表单数据列表")
    @GetMapping("/form/{formCode}")
    public List<VFormDataVO> getByFormCode(@PathVariable String formCode) {
        List<VFormData> formDataList = vFormDataService.getByFormCode(formCode);
        return formDataList.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Operation(summary = "根据模板ID获取表单数据列表")
    @GetMapping("/template/{templateId}")
    public List<VFormDataVO> getByTemplateId(@PathVariable Long templateId) {
        List<VFormData> formDataList = vFormDataService.getByTemplateId(templateId);
        return formDataList.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Operation(summary = "分页查询表单数据")
    @GetMapping("/page")
    public Page<VFormDataVO> page(@RequestParam(defaultValue = "1") Integer current,
                                @RequestParam(defaultValue = "10") Integer size) {
        Page<VFormData> page = new Page<>(current, size);
        Page<VFormData> result = vFormDataService.page(page);

        Page<VFormDataVO> voPage = new Page<>();
        BeanUtils.copyProperties(result, voPage);
        voPage.setRecords(result.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList()));
        return voPage;
    }

    @Operation(summary = "审核表单数据")
    @PutMapping("/review/{id}")
    public Boolean review(@PathVariable Long id,
                         @RequestParam Integer status,
                         @RequestParam(required = false) String comment,
                         @RequestParam Long reviewUserId,
                         @RequestParam String reviewUserName) {
        return vFormDataService.reviewFormData(id, status, comment, reviewUserId, reviewUserName);
    }

    @Operation(summary = "删除表单数据")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Long id) {
        return vFormDataService.deleteFormData(id);
    }

    private VFormDataVO convertToVO(VFormData formData) {
        if (formData == null) {
            return null;
        }
        VFormDataVO vo = new VFormDataVO();
        BeanUtils.copyProperties(formData, vo);
        return vo;
    }
}