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

  // 👇 首页
{
  path: "/dashboard",
  component: () => import("@/layout/index.vue"),
  children: [
    {
      path: "",
      name: "DashboardHome",
      component: () => import("@/views/dashboard/index.vue"),
      meta: { title: "首页", icon: "House" } // ← 这个会显示
    }
  ]
},

  // 👇 商品管理
  {
    path: "/product",
    name: "Product",
    component: () => import("@/layout/index.vue"),
    meta: { title: "商品管理", icon: "Box" },
    redirect: "/product",
    children: [
      {
        path: "",
        name: "ProductList",
        component: () => import("@/views/product/list/index.vue"),
        meta: { title: "商品列表", icon: "List" }
      }
    ]
  },
  {
    path: "/system",
    name: "System",
    component: () => import("@/layout/index.vue"),
    meta: { title: "系统管理", icon: "Settings" },
    redirect: "/system",
    children: [
      {
        path: "role",
        name: "Role",
        component: () => import("@/views/system/role/index.vue"),
        meta: { title: "权限管理", icon: "List" }
      }
    ]
  },
  // 👇 订单管理
  {
    path: "/order",
    name: "Order",
    component: () => import("@/layout/index.vue"),
    meta: { title: "订单管理", icon: "Tickets" },
    redirect: "/order",
    children: [
      {
        path: "cart",
        name: "Cart",
        component: () => import("@/views/order/cart/index.vue"),
        meta: { title: "购物车", icon: "ShoppingCart" }
      },
      {
        path: "",
        name: "OrderList",
        component: () => import("@/views/order/list/index.vue"),
        meta: { title: "我的订单", icon: "List" }
      },
      {
        path: "detail/:id",
        name: "OrderDetail",
        component: () => import("@/views/order/detail/index.vue"),
        meta: { title: "订单详情", hidden: true }
      }
    ]
  },

  // 👇 404 页面（必须放在最后！）
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/views/error/index.vue"),
    meta: { title: "页面不存在", hidden: true },
  },
];