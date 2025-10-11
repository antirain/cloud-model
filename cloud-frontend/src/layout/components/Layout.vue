<template>
  <div class="layout-container">
    <!-- 顶部导航栏 -->
    <header class="layout-header">
      <div class="header-content">
        <div class="logo">
          <el-icon><Setting /></el-icon>
          <span>{{ appTitle }}</span>
        </div>
        <div class="header-right">
          <el-dropdown trigger="hover" @command="handleUserCommand">
            <div class="user-info">
              <el-avatar :src="currentUser.avatar || ''">{{ currentUser.name?.charAt(0) }}</el-avatar>
              <span class="user-name">{{ currentUser.name }}</span>
              <!-- <el-icon><ChevronDown /></el-icon> -->
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="settings">设置</el-dropdown-item>
                <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>
    
    <!-- 主体内容 -->
    <main class="layout-main">
      <!-- 侧边栏导航 -->
      <aside class="layout-sidebar">
        <SidebarMenu />
      </aside>
      
      <!-- 内容区域 -->
      <div class="layout-content">
        <!-- 面包屑导航 -->
        <div class="breadcrumb-container">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item v-for="(item, index) in breadcrumbList" :key="index" :to="item.path">
              {{ item.name }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <!-- 路由视图 -->
        <div class="content-wrapper">
          <router-view />
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { Setting } from '@element-plus/icons-vue';
import SidebarMenu from './SidebarMenu.vue';
import getRouterManager from '../../utils/routerManager.js';

const router = useRouter();
const route = useRoute();
const routerManager = getRouterManager();

// 响应式数据
const appTitle = ref('Cloud Model System');
const currentUser = ref({
  id: '',
  name: '管理员',
  avatar: ''
});
const breadcrumbList = ref([]);

// 计算面包屑列表
const updateBreadcrumb = () => {
  const matchedRoutes = route.matched;
  breadcrumbList.value = matchedRoutes.map(item => ({
    name: item.meta.title || '首页',
    path: item.path
  }));
};

// 处理用户命令
const handleUserCommand = (command) => {
  switch (command) {
    case 'profile':
      console.log('查看个人中心');
      break;
    case 'settings':
      console.log('打开设置');
      break;
    case 'logout':
      handleLogout();
      break;
  }
};

// 处理退出登录
const handleLogout = () => {
  // 清除用户信息和token
  localStorage.removeItem('userId');
  localStorage.removeItem('token');
  
  // 重定向到登录页
  router.push('/login');
};

// 加载用户信息
const loadUserInfo = async () => {
  try {
    const userInfo = await routerManager.loadUserInfo();
    if (userInfo) {
      currentUser.value = userInfo;
    }
  } catch (error) {
    console.error('[Layout] 加载用户信息失败:', error);
  }
};

// 生命周期钩子
onMounted(() => {
  loadUserInfo();
  updateBreadcrumb();
  
  // 监听路由变化，更新面包屑
  router.afterEach(() => {
    updateBreadcrumb();
  });
});
</script>

<style scoped>
.layout-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
}

.layout-header {
  height: 60px;
  background-color: #ffffff;
  border-bottom: 1px solid #e8e8e8;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 100;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
}

.logo {
  display: flex;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
  color: #1890ff;
}

.logo .el-icon {
  margin-right: 8px;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-info .el-avatar {
  margin-right: 8px;
}

.user-name {
  margin-right: 5px;
}

.layout-main {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.layout-sidebar {
  width: 200px;
  background-color: #f0f2f5;
  border-right: 1px solid #e8e8e8;
  overflow-y: auto;
}

.layout-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.breadcrumb-container {
  padding: 15px 20px;
  background-color: #ffffff;
  border-bottom: 1px solid #e8e8e8;
}

.content-wrapper {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #fafafa;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .layout-sidebar {
    position: fixed;
    left: -200px;
    top: 60px;
    bottom: 0;
    z-index: 1000;
    transition: left 0.3s ease;
  }
  
  .layout-sidebar.open {
    left: 0;
  }
  
  .logo span {
    display: none;
  }
}
</style>