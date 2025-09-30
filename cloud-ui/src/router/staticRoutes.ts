// src/router/staticRoutes.ts
import type { RouteRecordRaw } from "vue-router";

export const staticRoutes: RouteRecordRaw[] = [
  // 👇 登录页（无需 Layout）
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/login/index.vue"),
    meta: { title: "登录", hidden: true }, // hidden: true 表示不在菜单中显示
  },

  // 👇 首页（如果你有 dashboard）
  {
    path: "/dashboard",
    name: "user",
    component: () => import("@/layout/index.vue"), // 使用 Layout
    meta: { title: "首页", icon: "dashboard" },
  },
  // 👇 404 页面（必须放在最后！）
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/views/error/index.vue"),
    meta: { title: "页面不存在", hidden: true },
  },
];
