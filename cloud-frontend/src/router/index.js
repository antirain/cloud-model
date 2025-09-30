import { createRouter, createWebHistory } from 'vue-router';

// 基础路由 - 无需权限即可访问的路由
const routes = [
  // {
  //   path: '/',
  //   name: 'Home',
  //   redirect: '/system'
  // },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: {
      title: '登录',
      requiresAuth: false
    }
  },
  // 404路由
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue'),
    meta: {
      title: '页面不存在',
      requiresAuth: false
    }
  }
];

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
});

// 路由拦截器，处理权限和页面标题
router.beforeEach(async (to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title + ' - 云管理系统';
  }
  
  // 判断是否需要登录
  const requiresAuth = to.meta.requiresAuth !== false;
  const token = localStorage.getItem('token');
  
  // 如果需要登录但没有token，跳转到登录页
  if (requiresAuth && !token) {
    return next('/login');
  }
  
  // 如果已经登录，访问登录页则重定向到首页
  if (to.path === '/login' && token) {
      return next('/login');
  }
  
  next();
});

export default router;