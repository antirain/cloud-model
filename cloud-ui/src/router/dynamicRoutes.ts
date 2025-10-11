// src/router/dynamicRoutes.ts
import { RouteRecordRaw } from "vue-router";
import components from './autoImportRoutes';

interface Menu {
  id: number;
  parentId: number;
  menuName: string;
  menuCode: string;
  menuType: number;
  path: string;
  component: string;
  icon: string;
  sort: number;
  status: number;
}

export function generateRoutes(menuList: Menu[]): RouteRecordRaw[] {
  // 过滤掉非菜单项（type=3 是按钮，不生成路由）和禁用的菜单项
  const menus = menuList.filter((m) => (m.menuType === 1 || m.menuType === 2) && m.status === 1);
  // 过滤掉非目录和菜单类型的菜单项

  // 构建父子关系树
  const map = new Map<number, RouteRecordRaw>();
  const roots: RouteRecordRaw[] = [];

  // 1. 首先创建所有路由记录并存入map
  menus.forEach((item) => {
    const routeRecord: RouteRecordRaw = {
      path: item.path,
      name: item.menuCode,
      meta: {
        title: item.menuName,
        icon: item.icon,
        code: item.menuCode,
        sort: item.sort,
        menuType: item.menuType
      },
      // 初始化redirect属性
      redirect: undefined
    };
    // 根据菜单类型设置不同的组件
    if (item.menuType === 1) {
      // 目录类型，使用布局组件
      routeRecord.component = () => import('@/layout/index.vue');
    } else {
      // 菜单类型，使用视图组件
      const componentPath = item.component;
      // 从组件映射表中获取组件
      const component = components[componentPath];
      if (component) {
        routeRecord.component = component;
      } else {
        // 组件不存在时，使用默认组件
        routeRecord.component = () => import(`@/views/${item.component}.vue`);
      }
    }
    
    // 初始化子路由数组
    routeRecord.children = [];
    
    map.set(item.id, routeRecord);
  });

  // 2. 构建父子关系
  menus.forEach((item) => {
    const currentRoute = map.get(item.id);
    
    if (item.parentId === 0) {
      // 顶级菜单直接加入根路由
      roots.push(currentRoute);
    } else {
      // 查找父级路由并添加子路由
      const parentRoute = map.get(item.parentId);
      
      if (parentRoute) {
        // 确保父级路由的children数组存在
        if (!parentRoute.children) {
          parentRoute.children = [];
        }
        
        // 添加当前路由到父级路由的children中
        parentRoute.children.push(currentRoute);
        
        // 更新父路由的redirect，指向第一个子路由
        if (parentRoute.children.length === 1 && !parentRoute.redirect) {
          parentRoute.redirect = { name: currentRoute.name };
        }
      }
    }
  });

  // 3. 按sort排序
  roots.sort((a, b) => (a.meta.sort || 0) - (b.meta.sort || 0));
  roots.forEach((route) => {
    if (route.children && route.children.length > 0) {
      route.children.sort((a, b) => (a.meta.sort || 0) - (b.meta.sort || 0));
    }
  });

  // 4. 过滤空子路由数组
  const filteredRoots = roots.map(route => {
    if (route.children && route.children.length === 0) {
      delete route.children;
    }
    return route;
  });

  console.log("[路由构建] 生成的动态路由:", filteredRoots);

  return filteredRoots;
}
