import createRequest from '@/api/request'
import { SERVICE_PREFIX } from '@/config/service.config'
import { LoginRequest } from '@/types/authTypes'

const authApi = createRequest(SERVICE_PREFIX.AUTH)

export function login(data: LoginRequest) {
  // 使用params选项传递查询参数(requestparam)
  return authApi.post('/login', null, {
    params: data
  })
}
