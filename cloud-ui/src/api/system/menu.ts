import createRequest from '@/api/request'
import { SERVICE_PREFIX } from '@/config/service.config'

const authApi = createRequest(SERVICE_PREFIX.SYSTEM)

export function menuList() {
  return authApi.get('/menu/list')
}

export function menuTree() {
  return authApi.get('/menu/tree')
}
