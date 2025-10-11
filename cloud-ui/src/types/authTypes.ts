// 登录请求体
export interface LoginRequest {
  username: string
  password: string
  captcha?: string // 可选字段
}

// 登录响应数据
export interface LoginResponse {
  token: string
  refreshToken?: string
}

// 用户信息
export interface UserInfo {
  id: number
  username: string
  nickname: string
  avatar: string
}