// src/router/index.ts
import { createRouter, createWebHistory } from "vue-router";
import { staticRoutes } from "./staticRoutes";
import { useUserStore } from "@/store/modules/user";

// 👇 合并所有路由（初始只有静态路由）
const routes = [...staticRoutes]; // 静态路由（登录、404 等）

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  console.log('路由守卫触发:', to.path, '登录状态:', userStore.isLogin)
  
  if (userStore.isLogin) {
    // 已登录，直接放行
    console.log('已登录，放行到:', to.path)
    next();
  } else {
    if (to.path === "/login") {
      console.log('未登录，访问登录页，放行')
      next();
    } else {
      console.log('未登录，重定向到登录页')
      next("/login");
    }
  }
});

export default router;
