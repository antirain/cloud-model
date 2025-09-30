import createRequest from '@/api/request'
import { SERVICE_PREFIX } from '@/config/service.config'

const authApi = createRequest(SERVICE_PREFIX.SYSTEM)

export function roleList() {
  return authApi.get('/sys-role/list')
}

