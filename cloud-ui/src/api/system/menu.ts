import createRequest from '@/api/request'
import { SERVICE_PREFIX } from '@/config/service.config'

const authApi = createRequest(SERVICE_PREFIX.SYSTEM)

export function menuList() {
  return authApi.get('/sys-menu/list')
}

export function menuTree() {
  return authApi.get('/sys-menu/tree')
}

export function getMenuById(id: number) {
  return authApi.get(`/sys-menu/user/${id}`)
}