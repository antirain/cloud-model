import API_CONFIG from '../apiConfig.js'
import { createAxiosInstance } from '../../utils/http.js'
import axios from 'axios'

// 创建带有system模块前缀的axios实例
const systemRequest = createAxiosInstance(API_CONFIG.system.prefix)

// 菜单管理API
export const menuApi = {
  // 获取菜单树
  getTree: () => systemRequest.get('/menu/tree'),
  
  // 获取菜单列表
  getList: (params) => systemRequest.get('/menu/list', { params }),
  
  // 获取角色菜单
  getRoleMenus: (roleId) => systemRequest.get(`/menu/role/${roleId}`),
  
  // 获取用户菜单
  getUserMenus: (userId) => systemRequest.get(`/menu/user/${userId}`),
  
  // 分配菜单
  assignMenus: (data) => systemRequest.post('/menu/assign', data),
  
  // 保存菜单
  save: (data) => systemRequest.post('/menu/save', data),
  
  // 删除菜单
  deleteMenu: (menuIds) => systemRequest.post('/menu/delete', menuIds)
}

export default menuApi