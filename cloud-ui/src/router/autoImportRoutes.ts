import { DefineComponent, AsyncComponentLoader } from 'vue';

// 使用 import.meta.glob 扫描所有 .vue 文件（懒加载模式，推荐用于路由）
const modules = import.meta.glob<DefineComponent>(
  '../views/**/*.vue',
  { eager: false } // 懒加载：按需加载，适合路由
);

// 如果你希望 eager 加载（开发时更快，但打包体积略大），可设为 { eager: true }

// 构建组件映射表：key 为标准化路径（如 'system/user/index'），value 为异步组件
export type ComponentMap = Record<string, AsyncComponentLoader>;

const components: ComponentMap = {};

for (const path in modules) {
  // 将路径 ../views/system/user/index.vue 转为 system/user/index
  const key = path
    .replace(/^(.*\/views\/)/g, '') 
    .replace(/\.vue$/, '')
    .replace(/\\/g, '/');
  console.log(key);
  
  components[key] = modules[path];
}

export default components;