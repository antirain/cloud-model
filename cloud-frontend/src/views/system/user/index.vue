<template>
  <div class="user-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="handleAddUser">
            <el-icon><Plus /></el-icon>新增用户
          </el-button>
        </div>
      </template>
      
      <!-- 搜索条件 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态">
            <el-option label="全部" value=""></el-option>
            <el-option label="启用" value="1"></el-option>
            <el-option label="禁用" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
      
      <!-- 用户列表 -->
      <el-table :data="userList" style="width: 100%" border>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="用户ID" width="80"></el-table-column>
        <el-table-column prop="username" label="用户名" width="120"></el-table-column>
        <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
        <el-table-column prop="phone" label="手机号" width="120"></el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-switch v-model="scope.row.status" @change="handleStatusChange(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEditUser(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDeleteUser(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.currentPage"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        ></el-pagination>
      </div>
    </el-card>
    
    <!-- 用户编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="formData.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item v-if="!formData.id" label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="formData.status"></el-switch>
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
          <el-select v-model="formData.roleIds" multiple placeholder="请选择角色">
            <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { Plus } from '@element-plus/icons-vue';
import userApi from '../../../api/system/user.js';
import roleApi from '../../../api/system/role.js';

// 搜索条件
const searchForm = reactive({
  username: '',
  status: ''
});

// 用户列表
const userList = ref([]);

// 分页信息
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
});

// 角色列表
const roleList = ref([]);

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref('新增用户');
const formData = reactive({
  id: '',
  username: '',
  nickname: '',
  password: '',
  email: '',
  phone: '',
  status: 1,
  roleIds: []
});
const formRef = ref();

// 表单验证规则
const formRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 20, message: '昵称长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  roleIds: [
    { required: true, message: '请至少选择一个角色', trigger: 'change' }
  ]
};

// 加载用户列表
const loadUserList = async () => {
  try {
    const params = {
      pageNum: pagination.currentPage,
      pageSize: pagination.pageSize,
      username: searchForm.username,
      status: searchForm.status
    };
    const res = await userApi.getPageList(params);
    userList.value = res.data?.records || [];
    pagination.total = res.data?.total || 0;
  } catch (error) {
    console.error('[用户管理] 加载用户列表失败:', error);
  }
};

// 加载角色列表
const loadRoleList = async () => {
  try {
    const res = await roleApi.getList();
    roleList.value = res.data || [];
  } catch (error) {
    console.error('[用户管理] 加载角色列表失败:', error);
  }
};

// 查询用户
const handleSearch = () => {
  pagination.currentPage = 1;
  loadUserList();
};

// 重置搜索
const resetSearch = () => {
  searchForm.username = '';
  searchForm.status = '';
  pagination.currentPage = 1;
  loadUserList();
};

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size;
  loadUserList();
};

// 当前页码改变
const handleCurrentChange = (current) => {
  pagination.currentPage = current;
  loadUserList();
};

// 新增用户
const handleAddUser = () => {
  dialogTitle.value = '新增用户';
  // 重置表单数据
  Object.assign(formData, {
    id: '',
    username: '',
    nickname: '',
    password: '',
    email: '',
    phone: '',
    status: 1,
    roleIds: []
  });
  dialogVisible.value = true;
};

// 编辑用户
const handleEditUser = async (user) => {
  dialogTitle.value = '编辑用户';
  // 复制用户数据到表单
  Object.assign(formData, {
    id: user.id,
    username: user.username,
    nickname: user.nickname,
    email: user.email,
    phone: user.phone,
    status: user.status,
    roleIds: []
  });
  
  // 加载用户角色
  try {
    const res = await systemApi.role.getUserRoles(user.id);
    formData.roleIds = res.data?.map(role => role.id) || [];
  } catch (error) {
    console.error('[用户管理] 加载用户角色失败:', error);
  }
  
  dialogVisible.value = true;
};

// 删除用户
const handleDeleteUser = async (userId) => {
  try {
    await ElMessageBox.confirm('确定要删除该用户吗？', '确认对话框', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    
    await systemApi.user.delete(userId);
    ElMessage.success('删除成功');
    loadUserList();
  } catch (error) {
    if (error !== 'cancel') {
      console.error('[用户管理] 删除用户失败:', error);
      ElMessage.error('删除失败');
    }
  }
};

// 改变用户状态
const handleStatusChange = async (user) => {
  try {
    await systemApi.user.updateStatus(user.id, user.status);
    ElMessage.success('状态更新成功');
  } catch (error) {
    console.error('[用户管理] 更新用户状态失败:', error);
    ElMessage.error('状态更新失败');
    // 恢复原来的状态
    user.status = !user.status;
  }
};

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate();
    
    const data = {
      ...formData,
      password: formData.password || undefined
    };
    
    if (formData.id) {
      // 更新用户
      await systemApi.user.update(data);
      ElMessage.success('更新成功');
    } else {
      // 新增用户
      await systemApi.user.save(data);
      ElMessage.success('新增成功');
    }
    
    // 关闭对话框
    dialogVisible.value = false;
    // 重新加载用户列表
    loadUserList();
  } catch (error) {
    console.error('[用户管理] 提交表单失败:', error);
    if (error !== 'cancel') {
      ElMessage.error('提交失败');
    }
  }
};

// 生命周期钩子
onMounted(() => {
  loadUserList();
  loadRoleList();
});
</script>

<style scoped>
.user-management {
  padding: 0 20px 20px 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>