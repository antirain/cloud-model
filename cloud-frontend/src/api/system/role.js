import API_CONFIG from '../apiConfig.js'
import { createAxiosInstance } from '../../utils/http.js'
import axios from 'axios'

// 创建带有system模块前缀的axios实例
const systemRequest = createAxiosInstance(API_CONFIG.system.prefix)

// 角色管理API
export const roleApi = {
  // 获取角色列表
  getList: (params) => systemRequest.get('/role/list', { params }),
  
  // 获取用户角色
  getUserRoles: (userId) => systemRequest.get(`/role/user/${userId}`),
  
  // 分配角色
  assignRoles: (data) => systemRequest.post('/role/assign', data),
  
  // 保存角色
  save: (data) => systemRequest.post('/role/save', data),
  
  // 删除角色
  deleteRole: (roleId) => systemRequest.delete(`/role/delete/${roleId}`),
  
  // 根据编码获取角色
  getByCode: (roleCode) => systemRequest.get(`/role/code/${roleCode}`)
}

export default roleApi