import userApi from './user.js'
import roleApi from './role.js'
import menuApi from './menu.js'

// 统一导出所有system相关的API模块
export default {
  user: userApi,
  role: roleApi,
  menu: menuApi
}