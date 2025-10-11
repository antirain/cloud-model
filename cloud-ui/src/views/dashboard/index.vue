<template>
  <div id="app">
    <v-form-designer ref="vfDesigner" :banned-widgets="testBanned"
                   :designer-config="designerConfig">
      <!-- 自定义按钮插槽演示 -->
      <template #customToolButtons>
        <el-button type="text" @click="saveFormJson">保存</el-button>
      </template>
    </v-form-designer>

  </div>
</template>

<script setup>
  import { ref, reactive } from 'vue'
  import { ElMessage } from 'element-plus'

  const vfDesigner = ref(null)
 
  const testBanned = ref([
    //'sub-form',
    //'alert',
  ])
  const designerConfig = reactive({
    languageMenu: true,
    //externalLink: false,
    //formTemplates: false,
    //eventCollapse: false,
    //clearDesignerButton: false,
    //previewFormButton: false,

    //presetCssCode: '.abc { font-size: 16px; }',
  })

  const saveFormJson = () => {
    let formJson = vfDesigner.value.getFormJson()
    //TODO: 将formJson提交给后端保存接口，需自行实现！！
    ElMessage.success('表单已保存！')
  }
</script>

<style lang="scss">
  #app {
    height: 100%;
  }
</style>
