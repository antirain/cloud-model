<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import getRouterManager from './utils/routerManager.js';

// 路由管理器
const routerManager = getRouterManager();

// 生命周期钩子
onMounted(() => {
  console.log('应用已挂载');
  // 初始化应用
  initApplication();
});

/**
 * 初始化应用
 * 主要负责路由和权限的初始化
 */
const initApplication = async () => {
  try {
    // 初始化路由（会加载用户信息和菜单）
    await routerManager.initRoutes();
  } catch (error) {
    console.error('应用初始化失败:', error);
  }
};
</script>

<style scoped>
#app {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}
</style>
