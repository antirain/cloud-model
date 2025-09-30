<!-- src/layout/index.vue -->
<template>
  <div class="app-layout">
    <!-- 顶部导航栏 -->
    <Header class="layout-header" />

    <div class="layout-container">
      <!-- 侧边栏 -->
      <Sidebar class="layout-sidebar" />

      <!-- 主内容区 -->
      <main class="layout-main">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import Header from '@/components/layout/Header.vue'
import Sidebar from '@/components/layout/Sidebar.vue'
</script>

<style scoped>
.app-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f5f5;
}

.layout-header {
  flex-shrink: 0;
}

.layout-container {
  display: flex;
  flex: 1;
  overflow: hidden;
}

.layout-sidebar {
  flex-shrink: 0;
  height: calc(100vh - 60px); /* 假设 Header 高 60px */
  overflow-y: auto;
}

.layout-main {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 响应式：小屏幕隐藏侧边栏 */
@media (max-width: 768px) {
  .layout-sidebar {
    display: none;
  }
}
</style>