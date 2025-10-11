<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import { login } from '@/api/auth/auth'
import { useUserStore } from '@/store/modules/user';
import { useRouteStore } from '@/store/modules/route';
import { useRouter } from 'vue-router';
const loginFormRef = ref(null)

const formData = ref({
  username: '',
  password: ''
})
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}
const userStore = useUserStore();
const routeStore =useRouteStore();
const router = useRouter();
const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      login(formData.value).then(async res => {
        if (res.code === 200) {
          const data = res.data;
          ElMessage.success('登录成功')
          userStore.setToken(data.token);

           // 加载动态路由
          routeStore.generateRoutes();
          router.push('/dashboard'); // 跳转首页
        }
      }).catch(err => {
        ElMessage.error(err.message)
      })
    }
  })
}
</script>
<template>
  <div class="login-container">
    <!-- 全屏渐变背景 -->
    <div class="bg-gradient"></div>

    <!-- 登录卡片 -->
    <div class="login-box">
      <div class="login-logo">
        <h1>用户登录</h1>
      </div>

      <el-form :model="formData" :rules="rules" ref="loginFormRef" class="login-form" @submit.prevent="handleLogin">
        <el-form-item prop="username">
          <el-input 
            v-model="formData.username" 
            placeholder="请输入用户名" 
            clearable
            prefix-icon="User"
            class="login-input"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="formData.password"
            type="password"
            placeholder="请输入密码"
            show-password
            prefix-icon="Lock"
            class="login-input"
            @keyup.enter="handleLogin"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleLogin" class="login-btn">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<style scoped>
/* 重置一些默认样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 确保html和body充满整个屏幕 */
html, body {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.login-container {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

/* 全屏渐变背景 */
.bg-gradient {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: linear-gradient(135deg, #f0f1f1 0%, #fafafc 100%);
  z-index: 0;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  z-index: 1;
  text-align: center;
  backdrop-filter: blur(10px);
  animation: fadeInUp 0.6s ease-out;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-logo h1 {
  margin-bottom: 36px;
  color: #1f2d3d;
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.login-form .el-form-item {
  margin-bottom: 24px;
}

/* 确保输入框长度一致 */
.login-input {
  width: 100%;
  height: 44px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.login-input:focus-within {
  border-color: #409eff;
  box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.2);
}

.login-btn {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 8px;
  background: linear-gradient(135deg, #409eff 0%, #667eea 100%);
  border: none;
  color: white;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  transition: all 0.3s ease;
  cursor: pointer;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(64, 158, 255, 0.4);
}

.login-btn:active {
  transform: translateY(0);
}

/* 响应式设计 */
@media (max-width: 480px) {
  .login-box {
    width: 90%;
    padding: 32px 24px;
    margin: 0 16px;
  }
  
  .login-logo h1 {
    font-size: 24px;
  }
}
</style>