// src/store/modules/route.ts
import { defineStore } from "pinia";
import { RouteRecordRaw } from "vue-router";
import { generateRoutes } from "@/router/dynamicRoutes";
import { getMenuById } from "@/api/system/menu";
import { useUserStore } from "@/store/modules/user";
import router from "@/router";

export const useRouteStore = defineStore("route", {
  state: () => ({
    isDynamicRouteAdded: false,
  }),

  actions: {
    async generateRoutes() {
      try {
        // 1. 调用你的菜单接口（替换成你的真实 API）
        const response = await getMenuById(2);
        const menus = response.data || [];
        console.log('接口返回',menus);
        
        // 2. 生成路由配置
        const routes = generateRoutes(menus); // 返回 RouteRecordRaw[]
        sessionStorage.setItem('dynamicRoutes', JSON.stringify(routes))
        
        // 3. 添加到 Vue Router（挂载到 Layout 下）
        routes.forEach((route) => {
          router.addRoute(route); // 注意：父路由 name 必须是 'System'
          console.log("添加路由:", route.path);
        });
        this.isDynamicRouteAdded = true;
      } catch (error) {
        console.error("生成动态路由失败:", error);
        throw error;
      }
    },

    reset() {
      this.isDynamicRouteAdded = false;
    },
  },
});
