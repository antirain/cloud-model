// 网关统一入口（开发环境代理到 /dev-api，生产环境直接部署到根路径）
const GATEWAY_BASE = import.meta.env.PROD ? '' : '/dev-api'

export const SERVICE_PREFIX = {
  AUTH: `${GATEWAY_BASE}/auth`,
  SYSTEM: `${GATEWAY_BASE}/system`,
  FILE: `${GATEWAY_BASE}/file`,
  ORDER: `${GATEWAY_BASE}/order`,
  PRODUCT: `${GATEWAY_BASE}/product`
}