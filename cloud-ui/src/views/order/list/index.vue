<template>
  <div class="order-page">
    <div class="page-header">
      <h2>我的订单</h2>
    </div>

    <div class="order-filter">
      <el-radio-group v-model="filterStatus" @change="loadOrderList">
        <el-radio-button :label="undefined">全部</el-radio-button>
        <el-radio-button :label="1">待支付</el-radio-button>
        <el-radio-button :label="2">待发货</el-radio-button>
        <el-radio-button :label="3">已发货</el-radio-button>
        <el-radio-button :label="4">已完成</el-radio-button>
      </el-radio-group>
    </div>

    <div class="order-content">
      <div v-if="loading" class="loading">
        <el-skeleton :rows="5" animated />
      </div>

      <div v-else-if="orderList.length === 0" class="empty-order">
        <el-empty description="暂无订单" />
      </div>

      <div v-else class="order-list">
        <div v-for="order in orderList" :key="order.id" class="order-card">
          <div class="order-header">
            <div class="order-info">
              <span class="order-no">订单号：{{ order.orderNo }}</span>
              <span class="order-time">下单时间：{{ formatTime(order.createTime) }}</span>
            </div>
            <div class="order-status">
              <el-tag :type="getStatusType(order.orderStatus)">
                {{ getStatusText(order.orderStatus) }}
              </el-tag>
            </div>
          </div>

          <div class="order-items">
            <div v-for="item in order.items" :key="item.id" class="order-item">
              <el-image 
                :src="item.productImage" 
                fit="cover" 
                class="product-image"
              >
                <template #error>
                  <div class="image-error">图片加载失败</div>
                </template>
              </el-image>
              <div class="item-info">
                <div class="product-name">{{ item.productName }}</div>
                <div class="sku-name">{{ item.skuName }}</div>
                <div class="item-price">¥{{ item.price }} × {{ item.quantity }}</div>
              </div>
              <div class="item-total">¥{{ item.totalAmount }}</div>
            </div>
          </div>

          <div class="order-footer">
            <div class="order-amount">
              实付金额：<span class="amount">¥{{ order.actualPayAmount }}</span>
            </div>
            <div class="order-actions">
              <el-button v-if="order.orderStatus === 1" type="primary" @click="payOrder(order.id)">
                立即支付
              </el-button>
              <el-button v-if="order.orderStatus === 1" @click="cancelOrder(order.id)">
                取消订单
              </el-button>
              <el-button v-if="order.orderStatus === 3" type="success" @click="confirmOrder(order.id)">
                确认收货
              </el-button>
              <el-button @click="viewOrderDetail(order.id)">
                查看详情
              </el-button>
            </div>
          </div>
        </div>
      </div>

      <!-- 分页 -->
      <div v-if="orderList.length > 0" class="pagination">
        <el-pagination
          v-model:current-page="pagination.current"
          v-model:page-size="pagination.size"
          :total="pagination.total"
          @current-change="handlePageChange"
          layout="total, prev, pager, next, jumper"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { getOrderList, cancelOrder, confirmOrder, payOrder, type OrderMain } from '@/api/order/order'

const router = useRouter()

// 响应式数据
const orderList = ref<OrderMain[]>([])
const loading = ref(false)
const filterStatus = ref<number | undefined>()

// 分页
const pagination = ref({
  current: 1,
  size: 10,
  total: 0
})

// 订单状态映射
const statusMap = {
  1: { text: '待支付', type: 'warning' },
  2: { text: '待发货', type: 'info' },
  3: { text: '已发货', type: 'primary' },
  4: { text: '已完成', type: 'success' },
  5: { text: '已取消', type: 'danger' },
  6: { text: '已关闭', type: 'info' }
}

// 生命周期
onMounted(() => {
  loadOrderList()
})

// 方法
const loadOrderList = async () => {
  try {
    loading.value = true
    const params = {
      current: pagination.value.current,
      size: pagination.value.size,
      orderStatus: filterStatus.value
    }
    const response = await getOrderList(params)
    orderList.value = response.records
    pagination.value.total = response.total
  } catch (error) {
    ElMessage.error('获取订单列表失败')
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

const formatTime = (time: string) => {
  return new Date(time).toLocaleString()
}

const handlePageChange = (page: number) => {
  pagination.value.current = page
  loadOrderList()
}

const viewOrderDetail = (orderId: number) => {
  router.push(`/order/detail/${orderId}`)
}

const cancelOrder = async (orderId: number) => {
  try {
    await ElMessageBox.confirm('确定要取消订单吗？', '提示', {
      type: 'warning'
    })
    await cancelOrder(orderId)
    ElMessage.success('取消订单成功')
    loadOrderList()
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
    loadOrderList()
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
.order-page {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.order-filter {
  margin-bottom: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}

.order-content {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
}

.loading {
  padding: 40px 0;
}

.empty-order {
  padding: 60px 0;
  text-align: center;
}

.order-card {
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  margin-bottom: 20px;
  padding: 20px;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 15px;
  border-bottom: 1px solid #f0f0f0;
}

.order-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.order-no {
  font-weight: 500;
  color: #333;
}

.order-time {
  color: #666;
  font-size: 14px;
}

.order-items {
  margin-bottom: 15px;
}

.order-item {
  display: flex;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #f5f5f5;
}

.order-item:last-child {
  border-bottom: none;
}

.product-image {
  width: 60px;
  height: 60px;
  border-radius: 4px;
  margin-right: 15px;
}

.item-info {
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

.item-price {
  color: #999;
  font-size: 14px;
}

.item-total {
  width: 100px;
  text-align: right;
  font-weight: 500;
  color: #ff4444;
}

.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 15px;
  border-top: 1px solid #f0f0f0;
}

.amount {
  font-size: 18px;
  font-weight: 600;
  color: #ff4444;
}

.order-actions {
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}
</style>