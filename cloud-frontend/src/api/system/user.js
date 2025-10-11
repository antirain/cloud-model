import API_CONFIG from '../apiConfig.js'
import { createAxiosInstance } from '../../utils/http.js'
import axios from 'axios'

// 创建带有system模块前缀的axios实例
const systemRequest = createAxiosInstance(API_CONFIG.system.prefix)

// 用户管理API
export const userApi = {
  // 获取用户分页列表
  getPageList: (params) => systemRequest.get('/user/page', { params }),
  
  // 获取用户详情
  getById: (id) => systemRequest.get(`/user/${id}`),
  
  // 新增用户
  save: (data) => systemRequest.post('/user', data),
  
  // 更新用户
  update: (data) => systemRequest.put('/user', data),
  
  // 删除用户
  deleteUser: (userId) => systemRequest.delete(`/user/${userId}`),
  
  // 修改用户状态
  updateStatus: (id, status) => systemRequest.put(`/user/${id}/status/${status}`),
  
  // 检查用户名是否存在
  checkUsername: (username) => systemRequest.get('/user/check-username', { params: { username } })
}

export default userApi