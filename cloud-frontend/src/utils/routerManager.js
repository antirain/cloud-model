import { useRouter } from 'vue-router';
import menuApi from '../api/system/menu.js';
import { ElMessage } from 'element-plus';

/**
 * 路由管理器
 * 处理动态路由注册、权限校验等功能
 */
export class RouterManager {
  constructor() {
    this.router = useRouter();
    this.dynamicRoutes = [];
    this.isRoutesLoaded = false;
  }

  /**
   * 初始化路由
   * 1. 加载用户信息
   * 2. 加载用户菜单
   * 3. 动态注册路由
   */
  async initRoutes() {
    try {
      // 加载用户信息
      await this.loadUserInfo();
      // 加载用户菜单
      const menus = await this.loadUserMenus();
      // 构建路由
      this.buildRoutes(menus);
      // 注册路由
      this.registerRoutes();
      
      this.isRoutesLoaded = true;
      return true;
    } catch (error) {
      console.error('[RouterManager] 初始化路由失败:', error);
      ElMessage.error('路由初始化失败，请刷新页面重试');
      return false;
    }
  }

  /**
   * 加载用户信息
   * 在实际项目中，这里应该调用API获取用户信息
   */
  async loadUserInfo() {
    // 模拟加载用户信息
    // 实际项目中应该调用 systemApi.user.getUserInfo()
    return {
      id: '1',
      name: '管理员',
      avatar: '',
      permissions: ['*']
    };
  }

  /**
   * 加载用户菜单
   * 从后端获取用户有权限的菜单列表
   */
  async loadUserMenus() {
    try {
      // 实际项目中，应该调用API获取用户菜单
      const userId = localStorage.getItem('userId') || '1'; // 假设默认用户ID为1
      const res = await menuApi.getUserMenus(userId);
      
      // 如果API返回数据，则使用API数据；否则使用默认菜单数据
      if (res && res.data && res.data.length > 0) {
        return this.transformMenuData(res.data);
      }
      
      // 模拟数据，根据用户提供的菜单表数据生成
      // 格式: id, parentId, name, code, level, path, component, icon
      const menuTableData = [
        { id: '1', parentId: '0', name: '系统管理', code: 'system', level: 1, path: '/system', component: 'Layout', icon: 'system' },
        { id: '2', parentId: '1', name: '用户管理', code: 'user_manage', level: 2, path: '/system/user', component: 'system/user/index', icon: 'user' },
        { id: '3', parentId: '1', name: '角色管理', code: 'role_manage', level: 2, path: '/system/role', component: 'system/role/index', icon: 'role' },
        { id: '4', parentId: '1', name: '菜单管理', code: 'menu_manage', level: 2, path: '/system/menu', component: 'system/menu/index', icon: 'menu' },
        { id: '5', parentId: '2', name: '新增用户', code: 'user_add', level: 3, path: '', component: '', icon: '' },
        { id: '6', parentId: '2', name: '编辑用户', code: 'user_edit', level: 3, path: '', component: '', icon: '' },
        { id: '7', parentId: '2', name: '删除用户', code: 'user_delete', level: 3, path: '', component: '', icon: '' },
        { id: '8', parentId: '3', name: '新增角色', code: 'role_add', level: 3, path: '', component: '', icon: '' },
        { id: '9', parentId: '3', name: '编辑角色', code: 'role_edit', level: 3, path: '', component: '', icon: '' },
        { id: '10', parentId: '3', name: '删除角色', code: 'role_delete', level: 3, path: '', component: '', icon: '' },
        { id: '11', parentId: '4', name: '新增菜单', code: 'menu_add', level: 3, path: '', component: '', icon: '' },
        { id: '12', parentId: '4', name: '编辑菜单', code: 'menu_edit', level: 3, path: '', component: '', icon: '' },
        { id: '13', parentId: '4', name: '删除菜单', code: 'menu_delete', level: 3, path: '', component: '', icon: '' }
      ];
      
      return this.buildMenuTree(menuTableData);
    } catch (error) {
      console.error('[RouterManager] 加载用户菜单失败:', error);
      // 如果API调用失败，返回模拟菜单数据
      const menuTableData = [
        { id: '1', parentId: '0', name: '系统管理', code: 'system', level: 1, path: '/system', component: 'Layout', icon: 'system' },
        { id: '2', parentId: '1', name: '用户管理', code: 'user_manage', level: 2, path: '/system/user', component: 'system/user/index', icon: 'user' },
        { id: '3', parentId: '1', name: '角色管理', code: 'role_manage', level: 2, path: '/system/role', component: 'system/role/index', icon: 'role' },
        { id: '4', parentId: '1', name: '菜单管理', code: 'menu_manage', level: 2, path: '/system/menu', component: 'system/menu/index', icon: 'menu' }
      ];
      return this.buildMenuTree(menuTableData);
    }
  }

  /**
   * 转换菜单数据格式
   * 将API返回的菜单数据转换为前端需要的格式
   */
  transformMenuData(menuData) {
    // 根据实际API返回格式进行转换
    return menuData;
  }

  /**
   * 根据菜单表数据构建菜单树
   * 将扁平的菜单数据转换为嵌套的树状结构
   */
  buildMenuTree(menuTableData) {
    const menuMap = new Map();
    const menuTree = [];
    
    // 只处理有路径的菜单项（忽略操作类型的菜单项）
    const validMenus = menuTableData.filter(menu => menu.path && menu.path.trim() !== '');
    
    // 构建ID到菜单的映射
    validMenus.forEach(menu => {
      menuMap.set(menu.id, {
        id: menu.id,
        parentId: menu.parentId,
        name: menu.name,
        icon: menu.icon,
        path: menu.path,
        component: menu.component,
        children: []
      });
    });
    
    // 构建树状结构
    validMenus.forEach(menu => {
      const currentMenu = menuMap.get(menu.id);
      const parentMenu = menuMap.get(menu.parentId);
      
      if (parentMenu) {
        parentMenu.children.push(currentMenu);
      } else if (menu.parentId === '0') {
        // 根节点菜单
        menuTree.push(currentMenu);
      }
    });
    
    return menuTree;
  }

  /**
   * 构建路由
   * 将菜单数据转换为路由配置
   */
  buildRoutes(menus) {
    this.dynamicRoutes = [];
    this.processMenuRoutes(menus, this.dynamicRoutes);
  }

  /**
   * 处理菜单路由
   * 递归处理嵌套菜单
   */
  processMenuRoutes(menus, routes) {
    menus.forEach(menu => {
      // 处理组件路径 - 将用户提供的组件路径格式转换为实际的导入路径
      let componentPath = null;
      if (menu.component && menu.component !== 'Layout') {
        // 使用静态路径前缀 + 动态组件路径的方式，确保ES模块动态导入正确解析
        componentPath = () => import.meta.glob('../views/**/*.vue')[`../views/${menu.component}.vue`]();
      }

      const route = {
        path: menu.path,
        name: menu.path.replace(/\//g, '') + 'Route',
        component: menu.component === 'Layout' ? () => import('../layout/components/Layout.vue') : componentPath,
        meta: {
          title: menu.name,
          icon: menu.icon,
          menuId: menu.id
        }
      };

      if (menu.children && menu.children.length > 0) {
        route.children = [];
        this.processMenuRoutes(menu.children, route.children);
      }

      // 只有有组件的菜单项才添加到路由
      if (componentPath || menu.component === 'Layout') {
        routes.push(route);
      }
    });
  }

  /**
   * 注册路由
   * 将构建好的路由添加到路由实例
   */
  registerRoutes() {
    this.dynamicRoutes.forEach(route => {
      this.router.addRoute(route);
    });
  }

  /**
   * 获取动态路由
   */
  getDynamicRoutes() {
    return this.dynamicRoutes;
  }

  /**
   * 判断路由是否已加载
   */
  routesLoaded() {
    return this.isRoutesLoaded;
  }
}

// 导出单例
let routerManager = null;
export const getRouterManager = () => {
  if (!routerManager) {
    routerManager = new RouterManager();
  }
  return routerManager;
};

export default getRouterManager;