<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <div class="error-code">404</div>
      <div class="error-message">页面不存在</div>
      <div class="error-description">抱歉，您访问的页面不存在或已被移除</div>
      <div class="error-actions">
        <el-button type="primary" @click="goHome">返回首页</el-button>
        <el-button @click="goBack">返回上一页</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

// 路由
const router = useRouter();

/**
 * 返回首页
 */
const goHome = () => {
  router.push('/');
};

/**
 * 返回上一页
 */
const goBack = () => {
  if (window.history.length > 1) {
    window.history.back();
  } else {
    router.push('/');
    ElMessage.info('已经是最前一页');
  }
};
</script>

<style scoped>
.not-found-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.not-found-content {
  text-align: center;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  max-width: 500px;
  width: 90%;
}

.error-code {
  font-size: 8rem;
  font-weight: 700;
  color: #1890ff;
  margin-bottom: 20px;
  line-height: 1;
}

.error-message {
  font-size: 2rem;
  font-weight: 500;
  color: #303133;
  margin-bottom: 10px;
}

.error-description {
  font-size: 1rem;
  color: #606266;
  margin-bottom: 30px;
}

.error-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .error-code {
    font-size: 5rem;
  }
  
  .error-message {
    font-size: 1.5rem;
  }
  
  .not-found-content {
    padding: 30px 20px;
  }
}
</style>