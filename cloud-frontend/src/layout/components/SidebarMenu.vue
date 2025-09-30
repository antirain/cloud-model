<template>
  <aside class="sidebar">
    <el-menu
      :default-active="activeMenu"
      class="sidebar-menu"
      router
      :collapse-transition="false"
      @select="handleMenuSelect"
    >
      <template v-for="menu in menuTree" :key="menu.id">
        <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.path">
          <template #title>
            <el-icon><component :is="getIconComponent(menu.icon)" /></el-icon>
            <span>{{ menu.name }}</span>
          </template>
          <el-menu-item
            v-for="subMenu in menu.children"
            :key="subMenu.id"
            :index="subMenu.path"
          >
            <el-icon><component :is="getIconComponent(subMenu.icon)" /></el-icon>
            <span>{{ subMenu.name }}</span>
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item v-else :index="menu.path">
          <el-icon><component :is="getIconComponent(menu.icon)" /></el-icon>
          <span>{{ menu.name }}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </aside>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import getRouterManager from '../../utils/routerManager.js';

// 路由相关
const route = useRoute();

// 响应式数据
const menuTree = ref([]);
const routerManager = getRouterManager();

// 计算当前激活的菜单
const activeMenu = computed(() => {
  return route.path || '/users';
});

// 处理菜单选择
const handleMenuSelect = (index) => {
  console.log('选择菜单:', index);
  // 可以在这里添加菜单选择的额外逻辑
};

// 图标映射 - 将用户提供的图标名称映射到Element Plus图标
const iconMap = {
  system: 'Setting',
  user: 'User',
  role: 'Key',
  menu: 'Menu'
};

// 获取图标组件
const getIconComponent = (iconName) => {
  // 如果直接提供的是Element Plus图标名称，则直接使用
  if (iconName && ElementPlusIconsVue[iconName]) {
    return ElementPlusIconsVue[iconName];
  }
  
  // 如果是自定义图标名称，则通过映射表转换
  const mappedIcon = iconName && iconMap[iconName.toLowerCase()];
  if (mappedIcon && ElementPlusIconsVue[mappedIcon]) {
    return ElementPlusIconsVue[mappedIcon];
  }
  
  // 默认图标
  return ElementPlusIconsVue.Menu;
};

// 加载菜单数据
const loadMenuData = async () => {
  try {
    // 确保路由已初始化
    if (!routerManager.routesLoaded()) {
      await routerManager.initRoutes();
    }
    
    // 直接从routerManager获取菜单树数据
    // 注意：这里假设routerManager需要提供一个getMenuTree方法
    // 在实际项目中，可能需要调整为合适的方法名或数据获取方式
    const menus = await routerManager.loadUserMenus();
    
    // 转换菜单数据为适合侧边栏显示的格式
    menuTree.value = menus.map(menu => ({
      id: menu.id,
      parentId: menu.parentId,
      name: menu.name,
      icon: menu.icon,
      path: menu.path,
      children: menu.children ? menu.children.map(child => ({
        id: child.id,
        parentId: child.parentId,
        name: child.name,
        icon: child.icon,
        path: child.path
      })) : []
    }));
  } catch (error) {
    console.error('[SidebarMenu] 加载菜单数据失败:', error);
  }
};

// 监听路由变化，更新菜单状态
const handleRouteChange = () => {
  // 路由变化时的处理逻辑
};

// 生命周期钩子
onMounted(() => {
  loadMenuData();
  // 监听路由变化
  const unsubscribe = route.meta && route.meta.onBeforeRouteUpdate ? 
    route.meta.onBeforeRouteUpdate(handleRouteChange) : null;
  
  return () => {
    if (unsubscribe) {
      unsubscribe();
    }
  };
});
</script>

<style scoped>
.sidebar {
  width: 200px;
  background-color: #f0f2f5;
  border-right: 1px solid #e8e8e8;
  overflow-y: auto;
  transition: width 0.3s ease;
}

.sidebar.collapsed {
  width: 64px;
}

.sidebar-menu {
  border-right: none;
}

/* 菜单图标样式 */
.el-icon {
  margin-right: 5px;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: -200px;
    top: 0;
    bottom: 0;
    z-index: 1000;
    transition: left 0.3s ease;
  }
  
  .sidebar.open {
    left: 0;
  }
}
</style>