<template>
  <div class="cart-page">
    <div class="page-header">
      <h2>购物车</h2>
      <div class="header-actions">
        <el-button type="primary" @click="batchRemoveSelected" :disabled="selectedItems.length === 0">
          批量删除
        </el-button>
        <el-button @click="selectAll" :disabled="cartItems.length === 0">
          {{ isAllSelected ? '取消全选' : '全选' }}
        </el-button>
      </div>
    </div>

    <div class="cart-content">
      <!-- 购物车列表 -->
      <div class="cart-list">
        <div v-if="cartItems.length === 0" class="empty-cart">
          <el-empty description="购物车为空" />
        </div>

        <div v-else class="cart-items">
          <div v-for="item in cartItems" :key="item.id" class="cart-item">
            <div class="item-selection">
              <el-checkbox 
                v-model="item.selected" 
                @change="updateItemSelection(item.id!, item.selected)"
              />
            </div>
            <div class="item-image">
              <el-image 
                :src="item.productImage" 
                fit="cover" 
                class="product-image"
              >
                <template #error>
                  <div class="image-error">图片加载失败</div>
                </template>
              </el-image>
            </div>
            <div class="item-info">
              <div class="product-name">{{ item.productName }}</div>
              <div class="sku-name">{{ item.skuName }}</div>
              <div class="price">¥{{ item.price }}</div>
            </div>
            <div class="item-quantity">
              <el-input-number 
                v-model="item.quantity" 
                :min="1" 
                :max="999"
                @change="updateItemQuantity(item.id!, item.quantity)"
              />
            </div>
            <div class="item-total">
              <div class="total-amount">¥{{ item.totalAmount }}</div>
              <el-button 
                type="danger" 
                link 
                @click="removeItem(item.id!)"
              >
                删除
              </el-button>
            </div>
          </div>
        </div>
      </div>

      <!-- 购物车汇总 -->
      <div v-if="cartItems.length > 0" class="cart-summary">
        <div class="summary-info">
          <span>已选 {{ selectedItems.length }} 件商品</span>
          <span class="total-price">总计：¥{{ selectedAmount }}</span>
        </div>
        <el-button type="primary" size="large" @click="goToCheckout">
          去结算
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getCartList, updateCartItem, removeCartItem, updateCartItemSelection, batchRemoveCartItems, batchUpdateSelection, type CartItem } from '@/api/order/cart'
import { useRouter } from 'vue-router'

const router = useRouter()

// 响应式数据
const cartItems = ref<CartItem[]>([])
const loading = ref(false)

// 计算属性
const selectedItems = computed(() => cartItems.value.filter(item => item.selected))
const isAllSelected = computed(() => cartItems.value.length > 0 && cartItems.value.every(item => item.selected))
const selectedAmount = computed(() => selectedItems.value.reduce((total, item) => total + item.totalAmount, 0))

// 生命周期
onMounted(() => {
  loadCartList()
})

// 方法
const loadCartList = async () => {
  try {
    loading.value = true
    const response = await getCartList()
    cartItems.value = response.items
  } catch (error) {
    ElMessage.error('获取购物车列表失败')
  } finally {
    loading.value = false
  }
}

const updateItemQuantity = async (id: number, quantity: number) => {
  try {
    await updateCartItem(id, { quantity })
    // 重新计算总价
    const item = cartItems.value.find(item => item.id === id)
    if (item) {
      item.totalAmount = item.price * quantity
    }
    ElMessage.success('更新数量成功')
  } catch (error) {
    ElMessage.error('更新数量失败')
  }
}

const updateItemSelection = async (id: number, selected: boolean) => {
  try {
    await updateCartItemSelection(id, selected)
  } catch (error) {
    ElMessage.error('更新选中状态失败')
  }
}

const removeItem = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该商品吗？', '提示', {
      type: 'warning'
    })
    await removeCartItem(id)
    cartItems.value = cartItems.value.filter(item => item.id !== id)
    ElMessage.success('删除成功')
  } catch (error) {
    // 用户取消删除
  }
}

const batchRemoveSelected = async () => {
  if (selectedItems.value.length === 0) {
    ElMessage.warning('请选择要删除的商品')
    return
  }

  try {
    await ElMessageBox.confirm(`确定要删除选中的 ${selectedItems.value.length} 件商品吗？`, '提示', {
      type: 'warning'
    })
    const ids = selectedItems.value.map(item => item.id!).filter(Boolean)
    await batchRemoveCartItems(ids)
    cartItems.value = cartItems.value.filter(item => !item.selected)
    ElMessage.success('批量删除成功')
  } catch (error) {
    // 用户取消删除
  }
}

const selectAll = async () => {
  try {
    const selected = !isAllSelected.value
    await batchUpdateSelection(selected)
    cartItems.value.forEach(item => {
      item.selected = selected
    })
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const goToCheckout = () => {
  if (selectedItems.value.length === 0) {
    ElMessage.warning('请选择要结算的商品')
    return
  }
  router.push('/order/checkout')
}
</script>

<style scoped>
.cart-page {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.cart-content {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.cart-item:last-child {
  border-bottom: none;
}

.item-selection {
  width: 60px;
  text-align: center;
}

.item-image {
  width: 80px;
  height: 80px;
  margin-right: 15px;
}

.product-image {
  width: 100%;
  height: 100%;
  border-radius: 4px;
}

.image-error {
  width: 100%;
  height: 100%;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}

.item-info {
  flex: 1;
  margin-right: 15px;
}

.product-name {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 5px;
}

.sku-name {
  color: #666;
  font-size: 14px;
  margin-bottom: 5px;
}

.price {
  color: #ff4444;
  font-size: 16px;
  font-weight: 500;
}

.item-quantity {
  width: 120px;
}

.item-total {
  width: 120px;
  text-align: right;
}

.total-amount {
  font-size: 16px;
  font-weight: 500;
  color: #ff4444;
  margin-bottom: 5px;
}

.cart-summary {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #f0f0f0;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.summary-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.total-price {
  font-size: 18px;
  font-weight: 600;
  color: #ff4444;
}

.empty-cart {
  text-align: center;
  padding: 60px 0;
}
</style>