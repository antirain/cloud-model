<!-- src/components/layout/Sidebar.vue -->
<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import { 
  House,
  Box,
  Tickets,
  ShoppingCart,
  List,
  Expand,
  Fold
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const isCollapse = ref(false)

// 定义路由元数据中图标的类型
type IconType = 'House' | 'Box' | 'Tickets' | 'ShoppingCart' | 'List' | string;

// 图标映射
const iconMap: Record<IconType, any> = {
  House,
  Box,
  Tickets,
  ShoppingCart,
  List
}

const activeMenu = computed(() => route.path)

// 获取所有菜单数据
const menuRoutes = computed(() => {
  const allRoutes = router.getRoutes()
  
  // 查找顶级路由
  const topLevelRoutes = allRoutes.filter(route => {
    if (route.meta?.hidden) return false
    
    const isChildRoute = allRoutes.some(parentRoute => {
      return parentRoute.children && 
             parentRoute.children.some(child => child.path === route.path) &&
             parentRoute.path !== route.path
    })
    
    return !isChildRoute && route.path !== '/'
  })
  console.log(topLevelRoutes);
  
  return topLevelRoutes
})

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}
</script>

<template>
  <el-aside class="app-sidebar">
    <el-menu
      :default-active="activeMenu"
      router
      class="sidebar-menu"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409eff"
      :collapse="isCollapse"
    >
      <template v-for="route in menuRoutes" :key="route.path">
        <!-- 有子菜单的路由 -->
        <el-sub-menu v-if="route.children && route.children.length > 0" :index="route.path">
          <template #title>
            <el-icon v-if="route.meta?.icon">
              <component :is="iconMap[route.meta.icon]" />
            </el-icon>
            <span>{{ route.meta?.title }}</span>
          </template>
          <el-menu-item 
            v-for="child in route.children" 
            :key="child.path" 
            :index="child.path === '' ? route.path : route.path + '/' + child.path"
          >
            <el-icon v-if="child.meta?.icon">
              <component :is="iconMap[child.meta.icon]" />
            </el-icon>
            <span>{{ child.meta?.title }}</span>
          </el-menu-item>
        </el-sub-menu>
        
        <!-- 没有子菜单的路由 -->
        <el-menu-item v-else :index="route.path">
          <el-icon v-if="route.meta?.icon">
            <component :is="iconMap[route.meta.icon]" />
          </el-icon>
          <span>{{ route.meta?.title }}</span>
        </el-menu-item>
      </template>
    </el-menu>
    
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="toggleCollapse">
      <el-icon>
        <component :is="isCollapse ? Expand : Fold" />
      </el-icon>
    </div>
  </el-aside>
</template>

<style scoped>
.app-sidebar {
  width: auto;
  background-color: #304156;
  position: relative;
  transition: width 0.3s;
}

.sidebar-menu {
  border: none;
  height: calc(100vh - 60px);
}

.collapse-btn {
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  text-align: center;
  padding: 12px;
  cursor: pointer;
  color: #bfcbd9;
  border-top: 1px solid #475669;
  transition: background-color 0.3s;
}

.collapse-btn:hover {
  background-color: #263445;
}

:deep(.el-menu--collapse) {
  width: 64px;
}

:deep(.el-menu-item) {
  height: 56px;
  line-height: 56px;
}

:deep(.el-menu-item .el-icon) {
  margin-right: 8px;
}

:deep(.el-sub-menu__title) {
  height: 56px;
  line-height: 56px;
}

:deep(.el-sub-menu__title .el-icon) {
  margin-right: 8px;
}
</style>