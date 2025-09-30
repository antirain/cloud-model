<template>
  <div class="order-detail-page">
    <div class="page-header">
      <el-button @click="$router.back()" type="primary" link>
        <el-icon><ArrowLeft /></el-icon>
        返回
      </el-button>
      <h2>订单详情</h2>
    </div>

    <div v-if="loading" class="loading">
      <el-skeleton :rows="10" animated />
    </div>

    <div v-else-if="orderDetail" class="order-detail">
      <!-- 订单基本信息 -->
      <el-card class="order-info-card">
        <template #header>
          <div class="card-header">
            <span>订单信息</span>
          </div>
        </template>
        
        <el-descriptions :column="2" border>
          <el-descriptions-item label="订单号">{{ orderDetail.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <el-tag :type="getStatusType(orderDetail.orderStatus)">
              {{ getStatusText(orderDetail.orderStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="支付状态">
            <el-tag :type="getPaymentStatusType(orderDetail.paymentStatus)">
              {{ getPaymentStatusText(orderDetail.paymentStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="支付方式">{{ getPaymentTypeText(orderDetail.paymentType) }}</el-descriptions-item>
          <el-descriptions-item label="下单时间">{{ formatTime(orderDetail.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="支付时间" v-if="orderDetail.paymentTime">
            {{ formatTime(orderDetail.paymentTime) }}
          </el-descriptions-item>
        </el-descriptions>
      </el-card>

      <!-- 收货信息 -->
      <el-card class="shipping-info-card">
        <template #header>
          <div class="card-header">
            <span>收货信息</span>
          </div>
        </template>
        
        <div class="shipping-info">
          <div class="consignee">
            <span class="label">收货人：</span>
            <span>{{ orderDetail.consigneeName }}</span>
          </div>
          <div class="phone">
            <span class="label">联系电话：</span>
            <span>{{ orderDetail.consigneePhone }}</span>
          </div>
          <div class="address">
            <span class="label">收货地址：</span>
            <span>{{ orderDetail.consigneeAddress }}</span>
          </div>
        </div>
      </el-card>

      <!-- 商品信息 -->
      <el-card class="product-info-card">
        <template #header>
          <div class="card-header">
            <span>商品信息</span>
          </div>
        </template>
        
        <div class="product-list">
          <div v-for="item in orderDetail.items" :key="item.id" class="product-item">
            <el-image 
              :src="item.productImage" 
              fit="cover" 
              class="product-image"
            >
              <template #error>
                <div class="image-error">图片加载失败</div>
              </template>
            </el-image>
            <div class="product-info">
              <div class="product-name">{{ item.productName }}</div>
              <div class="sku-name">{{ item.skuName }}</div>
              <div class="quantity">数量：{{ item.quantity }}</div>
            </div>
            <div class="product-price">
              <div class="price">¥{{ item.price }}</div>
              <div class="total">小计：¥{{ item.totalAmount }}</div>
            </div>
          </div>
        </div>
      </el-card>

      <!-- 价格信息 -->
      <el-card class="price-info-card">
        <template #header>
          <div class="card-header">
            <span>价格信息</span>
          </div>
        </template>
        
        <div class="price-list">
          <div class="price-item">
            <span class="label">商品总价：</span>
            <span>¥{{ orderDetail.totalAmount }}</span>
          </div>
          <div class="price-item">
            <span class="label">运费：</span>
            <span>¥{{ orderDetail.shippingFee }}</span>
          </div>
          <div class="price-item" v-if="orderDetail.discountAmount > 0">
            <span class="label">优惠金额：</span>
            <span>-¥{{ orderDetail.discountAmount }}</span>
          </div>
          <div class="price-item total">
            <span class="label">实付金额：</span>
            <span class="amount">¥{{ orderDetail.actualPayAmount }}</span>
          </div>
        </div>
      </el-card>

      <!-- 订单操作 -->
      <div class="order-actions" v-if="orderDetail.orderStatus === 1">
        <el-button type="primary" @click="payOrder(orderDetail.id)">
          立即支付
        </el-button>
        <el-button @click="cancelOrder(orderDetail.id)">
          取消订单
        </el-button>
      </div>

      <div class="order-actions" v-else-if="orderDetail.orderStatus === 3">
        <el-button type="success" @click="confirmOrder(orderDetail.id)">
          确认收货
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { ArrowLeft } from '@element-plus/icons-vue'
import { getOrderDetail, cancelOrder, confirmOrder, payOrder, type OrderMain } from '@/api/order/order'

const route = useRoute()
const router = useRouter()

const orderDetail = ref<OrderMain>()
const loading = ref(false)

// 状态映射
const statusMap = {
  1: { text: '待支付', type: 'warning' },
  2: { text: '待发货', type: 'info' },
  3: { text: '已发货', type: 'primary' },
  4: { text: '已完成', type: 'success' },
  5: { text: '已取消', type: 'danger' },
  6: { text: '已关闭', type: 'info' }
}

const paymentStatusMap = {
  1: { text: '未支付', type: 'warning' },
  2: { text: '已支付', type: 'success' },
  3: { text: '已退款', type: 'info' },
  4: { text: '部分退款', type: 'info' }
}

const paymentTypeMap = {
  1: '支付宝',
  2: '微信支付',
  3: '信用卡'
}

onMounted(() => {
  loadOrderDetail()
})

const loadOrderDetail = async () => {
  try {
    loading.value = true
    const orderId = parseInt(route.params.id as string)
    const response = await getOrderDetail(orderId)
    orderDetail.value = response
  } catch (error) {
    ElMessage.error('获取订单详情失败')
  } finally {
    loading.value = false
  }
}

const getStatusText = (status: number) => {
  return statusMap[status as keyof typeof statusMap]?.text || '未知状态'
}

const getStatusType = (status: number) => {
  return statusMap[status as keyof typeof statusMap]?.type || 'info'
}

const getPaymentStatusText = (status: number) => {
  return paymentStatusMap[status as keyof typeof paymentStatusMap]?.text || '未知状态'
}

const getPaymentStatusType = (status: number) => {
  return paymentStatusMap[status as keyof typeof paymentStatusMap]?.type || 'info'
}

const getPaymentTypeText = (type?: number) => {
  return type ? paymentTypeMap[type as keyof typeof paymentTypeMap] || '未知' : '未选择'
}

const formatTime = (time: string) => {
  return new Date(time).toLocaleString()
}

const cancelOrder = async (orderId: number) => {
  try {
    await ElMessageBox.confirm('确定要取消订单吗？', '提示', {
      type: 'warning'
    })
    await cancelOrder(orderId)
    ElMessage.success('取消订单成功')
    loadOrderDetail()
  } catch (error) {
    // 用户取消操作
  }
}

const confirmOrder = async (orderId: number) => {
  try {
    await ElMessageBox.confirm('确认收到商品了吗？', '提示', {
      type: 'warning'
    })
    await confirmOrder(orderId)
    ElMessage.success('确认收货成功')
    loadOrderDetail()
  } catch (error) {
    // 用户取消操作
  }
}

const payOrder = async (orderId: number) => {
  try {
    const response = await payOrder(orderId, 1) // 默认支付宝支付
    window.open(response.payUrl, '_blank')
  } catch (error) {
    ElMessage.error('支付失败')
  }
}
</script>

<style scoped>
.order-detail-page {
  padding: 20px;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 20px;
}

.loading {
  padding: 40px 0;
}

.order-detail {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.card-header {
  font-weight: 600;
  font-size: 16px;
}

.shipping-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.shipping-info .label {
  color: #666;
  min-width: 80px;
  display: inline-block;
}

.product-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.product-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
}

.product-image {
  width: 80px;
  height: 80px;
  border-radius: 4px;
  margin-right: 15px;
}

.image-error {
  width: 100%;
  height: 100%;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 12px;
}

.product-info {
  flex: 1;
}

.product-name {
  font-weight: 500;
  margin-bottom: 5px;
}

.sku-name {
  color: #666;
  font-size: 14px;
  margin-bottom: 5px;
}

.quantity {
  color: #999;
  font-size: 14px;
}

.product-price {
  text-align: right;
}

.price {
  font-size: 16px;
  font-weight: 500;
  color: #ff4444;
  margin-bottom: 5px;
}

.total {
  color: #666;
  font-size: 14px;
}

.price-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.price-item {
  display: flex;
  justify-content: space-between;
  padding: 5px 0;
}

.price-item.total {
  border-top: 1px solid #f0f0f0;
  padding-top: 15px;
  margin-top: 5px;
}

.price-item.total .amount {
  font-size: 18px;
  font-weight: 600;
  color: #ff4444;
}

.order-actions {
  text-align: center;
  padding: 20px 0;
}
</style>