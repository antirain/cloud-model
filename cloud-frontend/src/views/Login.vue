<template>
  <div class="login-container">
    <div class="login-form-wrapper">
      <div class="login-logo">
        <el-icon size="48"><HomeFilled /></el-icon>
        <h2>云管理系统</h2>
      </div>
      
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        label-position="left"
        class="login-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            clearable
          />
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="handleLogin"
            class="login-btn"
            :disabled="loading"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { HomeFilled, User, Lock } from '@element-plus/icons-vue';

// 路由
const router = useRouter();

// 表单引用
const loginFormRef = ref(null);

// 加载状态
const loading = ref(false);

// 登录表单数据
const loginForm = reactive({
  username: 'admin',
  password: 'admin123'
});

// 登录表单验证规则
const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度在 2 到 20 个字符之间', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符之间', trigger: 'blur' }
  ]
};

/**
 * 处理登录
 */
const handleLogin = async () => {
  try {
    // 验证表单
    await loginFormRef.value.validate();
    
    // 设置加载状态
    loading.value = true;
    
    // 模拟登录请求
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    // 在实际项目中，这里应该调用登录API
    // const res = await systemApi.auth.login(loginForm);
    // if (res.code === 200) {
    //   localStorage.setItem('token', res.data.token);
    //   localStorage.setItem('userInfo', JSON.stringify(res.data.userInfo));
    // }
    
    // 模拟成功登录，存储token
    localStorage.setItem('token', 'mock-token-123456');
    localStorage.setItem('userInfo', JSON.stringify({ id: '1', name: '管理员' }));
    
    ElMessage.success('登录成功');
    
    // 跳转到首页
    router.push('/users');
  } catch (error) {
    console.error('登录失败:', error);
    ElMessage.error('登录失败，请检查用户名和密码');
  } finally {
    loading.value = false;
  }
};

/**
 * 处理重置表单
 */
const handleReset = () => {
  loginFormRef.value.resetFields();
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-form-wrapper {
  background: white;
  border-radius: 8px;
  padding: 40px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
  width: 400px;
  max-width: 90%;
}

.login-logo {
  text-align: center;
  margin-bottom: 30px;
}

.login-logo h2 {
  margin: 10px 0 0 0;
  color: #303133;
  font-size: 24px;
}

.login-form {
  width: 100%;
}

.login-form .el-form-item {
  margin-bottom: 20px;
}

.login-btn {
  width: 100%;
  font-size: 16px;
  padding: 12px;
}

/* 响应式调整 */
@media screen and (max-width: 480px) {
  .login-form-wrapper {
    padding: 20px;
  }
}
</style>