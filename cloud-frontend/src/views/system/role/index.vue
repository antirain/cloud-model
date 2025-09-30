<template>
  <div class="role-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>角色管理</span>
          <el-button type="primary" @click="handleAddRole">
            <el-icon><Plus /></el-icon>新增角色
          </el-button>
        </div>
      </template>
      
      <!-- 搜索条件 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="角色名称">
          <el-input v-model="searchForm.name" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
      
      <!-- 角色列表 -->
      <el-table :data="roleList" style="width: 100%" border>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="角色ID" width="80"></el-table-column>
        <el-table-column prop="name" label="角色名称" width="120"></el-table-column>
        <el-table-column prop="code" label="角色编码" width="150"></el-table-column>
        <el-table-column prop="description" label="角色描述"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEditRole(scope.row)">编辑</el-button>
            <el-button type="primary" size="small" @click="handleAssignMenus(scope.row)">分配菜单</el-button>
            <el-button type="danger" size="small" @click="handleDeleteRole(scope.row.id)">删除</el-button>
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
    
    <!-- 角色编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="100px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="formData.code" placeholder="请输入角色编码"></el-input>
        </el-form-item>
        <el-form-item label="角色描述" prop="description">
          <el-input v-model="formData.description" type="textarea" placeholder="请输入角色描述"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
    
    <!-- 分配菜单对话框 -->
    <el-dialog v-model="menuDialogVisible" title="分配菜单" width="40%">
      <el-tree
        ref="menuTreeRef"
        v-model:checked-keys="checkedMenuIds"
        :data="menuTree"
        show-checkbox
        node-key="id"
        check-strictly
        :props="menuTreeProps"
        class="menu-tree"
      ></el-tree>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="menuDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAssignMenuSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { Plus } from '@element-plus/icons-vue';
import roleApi from '../../../api/system/role.js';
import menuApi from '../../../api/system/menu.js';

// 搜索条件
const searchForm = reactive({
  name: ''
});

// 角色列表
const roleList = ref([]);

// 分页信息
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
});

// 菜单树
const menuTree = ref([]);
const menuTreeProps = {
  label: 'name',
  children: 'children'
};
const menuTreeRef = ref();
const checkedMenuIds = ref([]);
const currentRoleId = ref('');

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref('新增角色');
const formData = reactive({
  id: '',
  name: '',
  code: '',
  description: ''
});
const formRef = ref();
const menuDialogVisible = ref(false);

// 表单验证规则
const formRules = {
  name: [
    { required: true, message: '请输入角色名称', trigger: 'blur' },
    { min: 2, max: 20, message: '角色名称长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入角色编码', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '角色编码只能包含字母、数字和下划线', trigger: 'blur' },
    { min: 2, max: 20, message: '角色编码长度在 2 到 20 个字符', trigger: 'blur' }
  ]
};

// 加载角色列表
const loadRoleList = async () => {
  try {
    const params = {
      pageNum: pagination.currentPage,
      pageSize: pagination.pageSize,
      name: searchForm.name
    };
    const res = await systemApi.role.getList(params);
    roleList.value = res.data?.records || [];
    pagination.total = res.data?.total || 0;
  } catch (error) {
    console.error('[角色管理] 加载角色列表失败:', error);
  }
};

// 加载菜单树
const loadMenuTree = async () => {
  try {
    const res = await menuApi.getTree();
    menuTree.value = res.data || [];
  } catch (error) {
    console.error('[角色管理] 加载菜单树失败:', error);
  }
};

// 加载角色菜单
const loadRoleMenus = async (roleId) => {
  try {
    const res = await menuApi.getRoleMenus(roleId);
    checkedMenuIds.value = res.data || [];
  } catch (error) {
    console.error('[角色管理] 加载角色菜单失败:', error);
    checkedMenuIds.value = [];
  }
};

// 查询角色
const handleSearch = () => {
  pagination.currentPage = 1;
  loadRoleList();
};

// 重置搜索
const resetSearch = () => {
  searchForm.name = '';
  pagination.currentPage = 1;
  loadRoleList();
};

// 分页大小改变
const handleSizeChange = (size) => {
  pagination.pageSize = size;
  loadRoleList();
};

// 当前页码改变
const handleCurrentChange = (current) => {
  pagination.currentPage = current;
  loadRoleList();
};

// 新增角色
const handleAddRole = () => {
  dialogTitle.value = '新增角色';
  // 重置表单数据
  Object.assign(formData, {
    id: '',
    name: '',
    code: '',
    description: ''
  });
  dialogVisible.value = true;
};

// 编辑角色
const handleEditRole = (role) => {
  dialogTitle.value = '编辑角色';
  // 复制角色数据到表单
  Object.assign(formData, {
    id: role.id,
    name: role.name,
    code: role.code,
    description: role.description
  });
  dialogVisible.value = true;
};

// 删除角色
const handleDeleteRole = async (roleId) => {
  try {
    await ElMessageBox.confirm('确定要删除该角色吗？', '确认对话框', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    
    await systemApi.role.delete(roleId);
    ElMessage.success('删除成功');
    loadRoleList();
  } catch (error) {
    if (error !== 'cancel') {
      console.error('[角色管理] 删除角色失败:', error);
      ElMessage.error('删除失败');
    }
  }
};

// 分配菜单
const handleAssignMenus = async (role) => {
  currentRoleId.value = role.id;
  await loadMenuTree();
  await loadRoleMenus(role.id);
  menuDialogVisible.value = true;
};

// 提交分配菜单
const handleAssignMenuSubmit = async () => {
  try {
    const data = {
      roleId: currentRoleId.value,
      menuIds: checkedMenuIds.value
    };
    
    await systemApi.menu.assignMenus(data);
    ElMessage.success('菜单分配成功');
    menuDialogVisible.value = false;
  } catch (error) {
    console.error('[角色管理] 菜单分配失败:', error);
    ElMessage.error('菜单分配失败');
  }
};

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate();
    
    const data = {
      ...formData
    };
    
    // 检查角色编码是否已存在
    const checkRes = await systemApi.role.getByCode(data.code);
    if (checkRes.data && checkRes.data.id !== data.id) {
      ElMessage.error('角色编码已存在');
      return;
    }
    
    if (data.id) {
      // 更新角色
      await systemApi.role.save(data);
      ElMessage.success('更新成功');
    } else {
      // 新增角色
      await systemApi.role.save(data);
      ElMessage.success('新增成功');
    }
    
    // 关闭对话框
    dialogVisible.value = false;
    // 重新加载角色列表
    loadRoleList();
  } catch (error) {
    console.error('[角色管理] 提交表单失败:', error);
    if (error !== 'cancel') {
      ElMessage.error('提交失败');
    }
  }
};

// 生命周期钩子
onMounted(() => {
  loadRoleList();
});
</script>

<style scoped>
.role-management {
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

.menu-tree {
  max-height: 400px;
  overflow-y: auto;
}
</style>