# Vue 单文件组件编码规范

## 文件结构顺序

所有 Vue 单文件组件 (SFC) 必须遵循以下顺序：

```vue
<script>
// 脚本部分
</script>

<template>
<!-- 模板部分 -->
</template>

<style>
/* 样式部分 */
</style>
```

## 脚本部分规范

### 导入顺序
导入语句应按以下顺序排列：

1. Vue 核心库
2. 第三方库
3. 组件
4. 工具/辅助函数
5. 类型定义 (TypeScript)
6. 资源 (图片、样式等)

```js
// Vue 核心库
import { ref, reactive, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'

// 第三方库
import { ElMessage } from 'element-plus'
import axios from 'axios'

// 组件
import AppHeader from '@/components/AppHeader.vue'
import UserAvatar from '@/components/UserAvatar.vue'

// 工具/辅助函数
import { formatDate } from '@/utils/date'
import { useUserStore } from '@/store/modules/user'

// 资源
import '@/assets/styles/common.css'
```

### 组件选项顺序 (Options API)

如果使用 Options API，选项应按以下顺序排列：

1. name
2. components
3. props
4. data
5. computed
6. watch
7. 生命周期钩子 (按照它们被调用的顺序)
8. methods

### Composition API 结构

如果使用 Composition API，应按以下结构组织代码：

1. 导入语句
2. Props 和 Emits 定义
3. 响应式状态定义
4. 计算属性
5. 生命周期钩子
6. 方法定义
7. 返回语句 (setup 函数)

## 模板部分规范

1. 组件名使用 PascalCase
2. 属性应按字母顺序排列
3. 指令应放在属性之后
4. 事件处理器应放在最后

```html
<UserProfile
  :avatar="userAvatar"
  :username="username"
  class="user-profile"
  v-if="isLoggedIn"
  @update="handleUpdate"
/>
```

## 样式部分规范

1. 优先使用 scoped 样式
2. 类名使用 kebab-case
3. 避免使用 !important
4. 样式应按以下顺序排列：
   - 定位属性 (position, top, right, z-index)
   - 盒模型属性 (display, box-sizing, width, height, margin, padding)
   - 排版属性 (font, line-height, text-align)
   - 视觉属性 (color, background, border, opacity)
   - 其他属性 (cursor, overflow, transition)

```css
.user-card {
  /* 定位 */
  position: relative;
  z-index: 1;
  
  /* 盒模型 */
  display: flex;
  width: 100%;
  padding: 16px;
  
  /* 排版 */
  font-size: 14px;
  line-height: 1.5;
  
  /* 视觉 */
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  
  /* 其他 */
  transition: all 0.3s ease;
}
```

## 命名约定

1. 组件文件名：使用 PascalCase (如 `UserProfile.vue`)
2. 基础组件：使用 `Base` 前缀 (如 `BaseButton.vue`)
3. 单例组件：使用 `The` 前缀 (如 `TheHeader.vue`)
4. 紧密耦合的组件：使用父组件名称作为前缀 (如 `UserProfileAvatar.vue`)

## 其他最佳实践

1. 组件应该是单一职责的
2. Props 应该有明确的类型和默认值
3. 避免在模板中使用复杂的表达式
4. 使用计算属性代替复杂的模板表达式
5. 使用 v-for 时必须提供 key
6. 避免 v-if 和 v-for 一起使用在同一元素上
7. 事件处理方法应该使用动词或动词短语命名

## 项目结构建议

```
src/
├── assets/          # 静态资源
├── components/      # 通用组件
│   ├── base/        # 基础组件
│   └── layout/      # 布局组件
├── composables/     # 组合式函数
├── config/          # 配置文件
├── router/          # 路由配置
├── store/           # 状态管理
├── types/           # TypeScript 类型定义
├── utils/           # 工具函数
└── views/           # 页面组件
```

## 工具配置

### ESLint 配置

建议在 `.eslintrc.js` 中添加以下规则：

```js
module.exports = {
  // ...其他配置
  rules: {
    'vue/component-definition-name-casing': ['error', 'PascalCase'],
    'vue/html-closing-bracket-newline': ['error', {
      'singleline': 'never',
      'multiline': 'always'
    }],
    'vue/html-indent': ['error', 2],
    'vue/max-attributes-per-line': ['error', {
      'singleline': 3,
      'multiline': 1
    }],
    'vue/order-in-components': ['error'],
    'vue/script-indent': ['error', 2, {
      'baseIndent': 0,
      'switchCase': 1
    }]
  }
}
```

### Vetur/Volar 配置

在 VS Code 中，可以配置 Vetur 或 Volar 来自动格式化代码：

```json
{
  "vetur.format.defaultFormatter.html": "prettier",
  "vetur.format.defaultFormatter.js": "prettier",
  "vetur.format.defaultFormatter.css": "prettier",
  "vetur.format.options.tabSize": 2,
  "vetur.format.options.useTabs": false
}
```

## 代码审查清单

在提交代码前，请检查以下内容：

- [ ] 组件结构顺序是否正确 (`<script>` → `<template>` → `<style>`)
- [ ] 导入语句是否按规定顺序排列
- [ ] 组件选项/Composition API 结构是否符合规范
- [ ] 命名是否符合约定
- [ ] 是否避免了复杂的模板表达式
- [ ] v-for 是否提供了 key
- [ ] 样式是否按规定顺序排列
- [ ] 是否避免了直接操作 DOM