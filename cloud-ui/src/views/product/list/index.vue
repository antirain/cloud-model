<template>
  <div class="product-page">
    <div class="page-header">
      <h2>商品列表</h2>
      <div class="header-actions">
        <el-button type="primary" @click="handleAdd">
          添加商品
        </el-button>
      </div>
    </div>

    <div class="product-content">
      <!-- 搜索筛选 -->
      <el-card class="search-card">
        <el-form :model="searchForm" inline>
          <el-form-item label="商品名称">
            <el-input 
              v-model="searchForm.productName" 
              placeholder="请输入商品名称"
              clearable
            />
          </el-form-item>
          <el-form-item label="商品分类">
            <el-select v-model="searchForm.categoryId" placeholder="请选择分类" clearable>
              <el-option 
                v-for="category in categories" 
                :key="category.id" 
                :label="category.categoryName" 
                :value="category.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="商品状态">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
              <el-option label="在售" :value="1" />
              <el-option label="下架" :value="2" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- 商品列表 -->
      <el-card class="product-list-card">
        <div v-if="loading" class="loading">
          <el-skeleton :rows="6" animated />
        </div>

        <div v-else-if="productList.length === 0" class="empty-product">
          <el-empty description="暂无商品" />
        </div>

        <div v-else class="product-grid">
          <div v-for="product in productList" :key="product.id" class="product-card">
            <div class="product-image">
              <el-image 
                :src="product.mainImage" 
                fit="cover" 
                class="image"
                :preview-src-list="[product.mainImage]"
              >
                <template #error>
                  <div class="image-error">
                    <el-icon><Picture /></el-icon>
                  </div>
                </template>
              </el-image>
              <div class="product-status">
                <el-tag :type="product.status === 1 ? 'success' : 'info'" size="small">
                  {{ product.status === 1 ? '在售' : '下架' }}
                </el-tag>
              </div>
            </div>
            
            <div class="product-info">
              <div class="product-name" :title="product.productName">
                {{ product.productName }}
              </div>
              <div class="product-desc" :title="product.description">
                {{ product.description }}
              </div>
              <div class="product-price">
                <span class="price-label">价格范围：</span>
                <span class="price-range">¥{{ product.minPrice }} - ¥{{ product.maxPrice }}</span>
              </div>
              <div class="product-stock">
                <span class="stock-label">总库存：</span>
                <span class="stock-value">{{ product.totalStock }}</span>
              </div>
            </div>

            <div class="product-actions">
              <el-button type="primary" size="small" @click="viewProduct(product.id)">
                查看
              </el-button>
              <el-button size="small" @click="editProduct(product.id)">
                编辑
              </el-button>
              <el-button 
                :type="product.status === 1 ? 'warning' : 'success'" 
                size="small"
                @click="toggleProductStatus(product)"
              >
                {{ product.status === 1 ? '下架' : '上架' }}
              </el-button>
              <el-button type="danger" size="small" @click="deleteProduct(product.id)">
                删除
              </el-button>
            </div>
          </div>
        </div>

        <!-- 分页 -->
        <div class="pagination">
          <el-pagination
            v-model:current-page="pagination.current"
            v-model:page-size="pagination.size"
            :total="pagination.total"
            @current-change="handlePageChange"
            layout="total, prev, pager, next, jumper"
          />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Picture } from '@element-plus/icons-vue'

// 模拟数据接口（实际项目中需要调用真实的API）
interface Product {
  id: number
  productName: string
  description: string
  mainImage: string
  categoryId: number
  status: number
  minPrice: number
  maxPrice: number
  totalStock: number
}

interface Category {
  id: number
  categoryName: string
}

// 响应式数据
const productList = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const searchForm = ref({
  productName: '',
  categoryId: undefined as number | undefined,
  status: undefined as number | undefined
})

// 分页
const pagination = ref({
  current: 1,
  size: 12,
  total: 0
})

// 模拟数据
const mockProducts: Product[] = [
  {
    id: 1,
    productName: 'iPhone 15 Pro',
    description: '最新款苹果手机，搭载A17芯片',
    mainImage: 'https://example.com/iphone15.jpg',
    categoryId: 1,
    status: 1,
    minPrice: 7999,
    maxPrice: 11999,
    totalStock: 100
  },
  {
    id: 2,
    productName: 'MacBook Air M2',
    description: '轻薄便携的笔记本电脑',
    mainImage: 'https://example.com/macbook.jpg',
    categoryId: 2,
    status: 1,
    minPrice: 8999,
    maxPrice: 12999,
    totalStock: 50
  }
]

const mockCategories: Category[] = [
  { id: 1, categoryName: '手机' },
  { id: 2, categoryName: '电脑' },
  { id: 3, categoryName: '平板' }
]

// 生命周期
onMounted(() => {
  loadProductList()
  loadCategories()
})

// 方法
const loadProductList = async () => {
  try {
    loading.value = true
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 500))
    productList.value = mockProducts
    pagination.value.total = mockProducts.length
  } catch (error) {
    ElMessage.error('获取商品列表失败')
  } finally {
    loading.value = false
  }
}

const loadCategories = async () => {
  try {
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 300))
    categories.value = mockCategories
  } catch (error) {
    ElMessage.error('获取分类列表失败')
  }
}

const handleSearch = () => {
  pagination.value.current = 1
  loadProductList()
}

const handleReset = () => {
  searchForm.value = {
    productName: '',
    categoryId: undefined,
    status: undefined
  }
  pagination.value.current = 1
  loadProductList()
}

const handlePageChange = (page: number) => {
  pagination.value.current = page
  loadProductList()
}

const handleAdd = () => {
  ElMessage.info('添加商品功能待实现')
}

const viewProduct = (id: number) => {
  ElMessage.info(`查看商品 ${id}`)
}

const editProduct = (id: number) => {
  ElMessage.info(`编辑商品 ${id}`)
}

const toggleProductStatus = async (product: Product) => {
  try {
    const action = product.status === 1 ? '下架' : '上架'
    await ElMessageBox.confirm(`确定要${action}该商品吗？`, '提示', {
      type: 'warning'
    })
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 300))
    product.status = product.status === 1 ? 2 : 1
    ElMessage.success(`${action}成功`)
  } catch (error) {
    // 用户取消操作
  }
}

const deleteProduct = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该商品吗？', '提示', {
      type: 'warning'
    })
    // 模拟API调用
    await new Promise(resolve => setTimeout(resolve, 300))
    productList.value = productList.value.filter(product => product.id !== id)
    ElMessage.success('删除成功')
  } catch (error) {
    // 用户取消操作
  }
}
</script>

<style scoped>
.product-page {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.product-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.search-card {
  margin-bottom: 0;
}

.product-list-card {
  min-height: 500px;
}

.loading {
  padding: 40px 0;
}

.empty-product {
  padding: 60px 0;
  text-align: center;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.product-card {
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  overflow: hidden;
  transition: box-shadow 0.3s;
}

.product-card:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.product-image {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
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

.product-status {
  position: absolute;
  top: 10px;
  right: 10px;
}

.product-info {
  padding: 15px;
}

.product-name {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-desc {
  color: #666;
  font-size: 14px;
  margin-bottom: 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-price, .product-stock {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  margin-bottom: 5px;
}

.price-label, .stock-label {
  color: #666;
}

.price-range {
  color: #ff4444;
  font-weight: 500;
}

.stock-value {
  color: #333;
  font-weight: 500;
}

.product-actions {
  padding: 15px;
  border-top: 1px solid #f0f0f0;
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.pagination {
  text-align: center;
  margin-top: 20px;
}
</style>