import axios from 'axios'

// API请求基础配置
const BASE_CONFIG = {
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
}

/**
 * 创建axios实例的工厂函数
 * @param {string} modulePrefix - 模块前缀
 * @returns {AxiosInstance} 配置好的axios实例
 */
function createAxiosInstance(modulePrefix = '') {
  const instance = axios.create(BASE_CONFIG)

  // 请求拦截器
  instance.interceptors.request.use(
    config => {
      // 添加模块前缀
      if (modulePrefix && config.url && !config.url.startsWith('http')) {
        config.url = modulePrefix + config.url
      }
      
      // 添加token等认证信息
      const token = localStorage.getItem('token')
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
      
      return config
    },
    error => {
      console.error('[API] 请求配置错误:', error)
      return Promise.reject(error)
    }
  )

  // 响应拦截器
  instance.interceptors.response.use(
    response => {
      const res = response.data
      // 根据后端返回的格式调整
      if (res.code && res.code !== 200) {
        console.error('[API] 请求失败:', res.msg || '未知错误')
        return Promise.reject(new Error(res.msg || '未知错误'))
      }
      return res
    },
    error => {
      console.error('[API] 网络请求错误:', error.message)
      return Promise.reject(error)
    }
  )

  return instance
}

// 默认axios实例（无前缀）
export const request = createAxiosInstance()

// 导出工厂函数
export { createAxiosInstance }

export default request