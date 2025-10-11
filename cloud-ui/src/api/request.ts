import axios from 'axios'

// 创建一个带通用配置的基础实例
const createRequest = (baseURL: string) => {
  const service = axios.create({
    baseURL,
    timeout: 10000
  })

  // 请求拦截器
  service.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
      return config
    },
    (error) => Promise.reject(error)
  )

  // 响应拦截器
  service.interceptors.response.use(
    (response) => response.data,
    (error) => {
      // 统一错误处理（如 401 跳登录）
      if (error.response?.status === 401) {
        localStorage.removeItem('token')
        window.location.href = '/login'
      }
      return Promise.reject(error)
    }
  )

  return service
}

export default createRequest