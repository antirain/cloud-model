<script setup lang="ts">
// 仪表板页面

</script>

<template>
  <div class="dashboard">
    <h2>系统概览</h2>
    <div class="stats-grid">
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">156</div>
          <div class="stat-label">总订单数</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">89</div>
          <div class="stat-label">今日订单</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">42</div>
          <div class="stat-label">商品总数</div>
        </div>
      </el-card>
      <el-card class="stat-card">
        <div class="stat-item">
          <div class="stat-value">¥12,568</div>
          <div class="stat-label">今日销售额</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.stat-card {
  text-align: center;
}

.stat-item {
  padding: 20px;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #409eff;
  margin-bottom: 8px;
}

.stat-label {
  color: #666;
  font-size: 14px;
}
</style>