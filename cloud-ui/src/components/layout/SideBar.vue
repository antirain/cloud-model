<!-- src/components/layout/Sidebar.vue -->
<template>
  <aside class="app-sidebar">
    <menu-list :items="menuRoutes" />
  </aside>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import MenuList from './MenuList.vue'

const router = useRouter()

// 获取所有菜单数据
const menuRoutes = computed(() => {
  // Vue Router 的 getRoutes() 方法会平铺返回所有路由，包括嵌套的子路由
  // 这里我们需要过滤出真正的顶级路由（不是任何其他路由的子路由）
  const allRoutes = router.getRoutes()
  
  // 查找顶级路由
  const topLevelRoutes = allRoutes.filter(route => {
    // 只考虑非隐藏的路由
    if (route.meta?.hidden) return false
    
    // 检查当前路由是否是另一个路由的子路由
    const isChildRoute = allRoutes.some(parentRoute => {
      return parentRoute.children && 
             parentRoute.children.some(child => child.path === route.path) &&
             parentRoute.path !== route.path
    })
    
    return !isChildRoute && route.path !== '/'
  })
  
  // 按排序字段排序
  return topLevelRoutes.sort((a, b) => {
    if (a.meta?.sort && b.meta?.sort) {
      return a.meta.sort - b.meta.sort
    }
    return 0
  })
})

</script>

<style scoped>
.app-sidebar {
  width: 220px;
  background-color: #fff;
  border-right: 1px solid #e8e8e8;
}

/* 小屏幕隐藏 */
@media (max-width: 768px) {
  .app-sidebar {
    display: none;
  }
}
</style>