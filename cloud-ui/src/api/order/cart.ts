import createRequest from '@/api/request'
import { SERVICE_PREFIX } from '@/config/service.config'

const orderApi = createRequest(SERVICE_PREFIX.ORDER)

export interface CartItem {
  id?: number
  productId: number
  skuId: number
  productName: string
  skuName: string
  productImage: string
  price: number
  quantity: number
  totalAmount: number
  selected: boolean
}

export interface CartListResponse {
  items: CartItem[]
  totalAmount: number
  selectedAmount: number
}

/**
 * 获取购物车列表
 */
export const getCartList = () => {
  return orderApi.get<CartListResponse>('/cart/list')
}

/**
 * 添加商品到购物车
 */
export const addToCart = (data: {
  productId: number
  skuId: number
  quantity: number
}) => {
  return orderApi.post('/cart/add', data)
}

/**
 * 更新购物车商品数量
 */
export const updateCartItem = (id: number, data: { quantity: number }) => {
  return orderApi.put(`/cart/update/${id}`, data)
}

/**
 * 删除购物车商品
 */
export const removeCartItem = (id: number) => {
  return orderApi.delete(`/cart/remove/${id}`)
}

/**
 * 批量删除购物车商品
 */
export const batchRemoveCartItems = (ids: number[]) => {
  return orderApi.post('/cart/batch-remove', { ids })
}

/**
 * 更新商品选中状态
 */
export const updateCartItemSelection = (id: number, selected: boolean) => {
  return orderApi.put(`/cart/selection/${id}`, { selected })
}

/**
 * 批量更新选中状态
 */
export const batchUpdateSelection = (selected: boolean) => {
  return orderApi.put('/cart/batch-selection', { selected })
}