// src/router/index.ts
import { createRouter, createWebHistory } from "vue-router";
import { staticRoutes } from "./staticRoutes";
import { useUserStore } from "@/store/modules/user";
import { useRouteStore } from "@/store/modules/route";

// 路由初始化状态
let isRoutesLoaded = false;

// 👇 合并所有路由（初始只有静态路由）
const routes = [...staticRoutes]; // 静态路由（登录、404 等）

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore();
  const routeStore = useRouteStore();
  if (userStore.isLogin) {
    if (!routeStore.isDynamicRouteAdded) {
      console.log("动态路由未添加，开始添加...");
      try {
        await routeStore.generateRoutes();
        // ⚠️ 关键：重新导航，让新路由生效
        next({ ...to, replace: true });
      } catch (error) {
        userStore.logout();
        next("/login");
      }
    } else {
      console.log("动态路由已添加，直接放行");
      next();
    }
  } else {
    if (to.path === "/login") {
      next();
    } else {
      next("/login");
    }
  }
});

export default router;
