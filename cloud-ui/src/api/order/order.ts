import createRequest from '@/api/request'
import { SERVICE_PREFIX } from '@/config/service.config'

const orderApi = createRequest(SERVICE_PREFIX.ORDER)

export interface OrderItem {
  id: number
  productId: number
  skuId: number
  productName: string
  skuName: string
  productImage: string
  quantity: number
  price: number
  totalAmount: number
  itemStatus: number
}

export interface OrderMain {
  id: number
  orderNo: string
  userId: number
  totalAmount: number
  actualPayAmount: number
  shippingFee: number
  discountAmount: number
  orderStatus: number
  paymentStatus: number
  paymentType: number
  paymentTime: string
  consigneeName: string
  consigneePhone: string
  consigneeAddress: string
  remark: string
  createTime: string
  items: OrderItem[]
}

export interface CreateOrderRequest {
  cartItemIds: number[]
  shippingAddressId: number
  couponId?: number
  remark?: string
}

export interface OrderListResponse {
  records: OrderMain[]
  total: number
  size: number
  current: number
}

/**
 * 创建订单
 */
export const createOrder = (data: CreateOrderRequest) => {
  return orderApi.post<{ orderId: number; orderNo: string }>('/order/create', data)
}

/**
 * 获取订单列表
 */
export const getOrderList = (params: {
  current: number
  size: number
  orderStatus?: number
}) => {
  return orderApi.get<OrderListResponse>('/order/list', { params })
}

/**
 * 获取订单详情
 */
export const getOrderDetail = (orderId: number) => {
  return orderApi.get<OrderMain>(`/order/detail/${orderId}`)
}

/**
 * 取消订单
 */
export const cancelOrder = (orderId: number) => {
  return orderApi.put(`/order/cancel/${orderId}`)
}

/**
 * 确认收货
 */
export const confirmOrder = (orderId: number) => {
  return orderApi.put(`/order/confirm/${orderId}`)
}

/**
 * 支付订单
 */
export const payOrder = (orderId: number, paymentType: number) => {
  return orderApi.post<{ payUrl: string }>(`/order/pay/${orderId}`, { paymentType })
}

/**
 * 删除订单
 */
export const deleteOrder = (orderId: number) => {
  return orderApi.delete(`/order/delete/${orderId}`)
}