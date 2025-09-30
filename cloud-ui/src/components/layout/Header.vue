<!-- src/components/layout/Header.vue -->
<template>
  <header class="app-header">
    <div class="header-left">
      <h1>我的后台系统</h1>
    </div>
    <div class="header-right">
      <span>欢迎，{{ username }}</span>
      <button @click="logout">退出</button>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const username = ref('管理员')

const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}
</script>

<style scoped>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
  padding: 0 20px;
  background-color: #1890ff;
  color: white;
}

.header-left h1 {
  margin: 0;
  font-size: 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

button {
  padding: 6px 12px;
  background: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>