<template>
  <div class="menu-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>菜单管理</span>
          <el-button type="primary" @click="handleAddRootMenu">
            <el-icon><Plus /></el-icon>新增根菜单
          </el-button>
        </div>
      </template>
      
      <!-- 搜索条件 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="菜单名称">
          <el-input v-model="searchForm.name" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
      
      <!-- 菜单表格 -->
      <el-table :data="menuList" style="width: 100%" border>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="菜单ID" width="80"></el-table-column>
        <el-table-column prop="name" label="菜单名称" min-width="150">
          <template #default="scope">
            <span :style="{ paddingLeft: scope.row.level * 20 + 'px' }">
              {{ scope.row.name }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="code" label="权限标识" min-width="150"></el-table-column>
        <el-table-column prop="path" label="路由路径" min-width="150"></el-table-column>
        <el-table-column prop="component" label="组件路径" min-width="150"></el-table-column>
        <el-table-column prop="type" label="菜单类型" width="100">
          <template #default="scope">
            <el-tag :type="getMenuTypeTag(scope.row.type)">
              {{ getMenuTypeName(scope.row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="排序" width="80"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleAddSubMenu(scope.row)">添加子菜单</el-button>
            <el-button type="primary" size="small" @click="handleEditMenu(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDeleteMenu(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 菜单编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="formData" :rules="formRules" ref="formRef" label-width="120px">
        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="formData.parentId" placeholder="请选择上级菜单" :disabled="isRootMenu">
            <el-option label="无" value="0"></el-option>
            <el-option v-for="item in menuTree" :key="item.id" :label="item.name" :value="item.id" v-show="!isSelfOrDescendant(item, formData.id)">
              <span :style="{ paddingLeft: item.level * 10 + 'px' }">
                {{ item.name }}
              </span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="权限标识" prop="code">
          <el-input v-model="formData.code" placeholder="请输入权限标识"></el-input>
        </el-form-item>
        <el-form-item label="路由路径" prop="path">
          <el-input v-model="formData.path" placeholder="请输入路由路径"></el-input>
        </el-form-item>
        <el-form-item label="组件路径" prop="component">
          <el-input v-model="formData.component" placeholder="请输入组件路径"></el-input>
        </el-form-item>
        <el-form-item label="菜单类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择菜单类型">
            <el-option label="目录" value="menu"></el-option>
            <el-option label="菜单" value="page"></el-option>
            <el-option label="按钮" value="button"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input-number v-model="formData.orderNum" :min="0" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="formData.icon" placeholder="请输入图标名称"></el-input>
        </el-form-item>
        <el-form-item label="是否缓存" prop="keepAlive">
          <el-switch v-model="formData.keepAlive"></el-switch>
        </el-form-item>
        <el-form-item label="是否隐藏" prop="hidden">
          <el-switch v-model="formData.hidden"></el-switch>
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
import { ref, reactive, onMounted, computed } from 'vue';
import { Plus } from '@element-plus/icons-vue';
import menuApi from '../../../api/system/menu.js';
import { ElMessageBox, ElMessage } from 'element-plus';

// 搜索条件
const searchForm = reactive({
  name: ''
});

// 菜单列表
const menuList = ref([]);

// 菜单树（用于选择上级菜单）
const menuTree = ref([]);

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref('新增根菜单');
const formData = reactive({
  id: '',
  parentId: '0',
  name: '',
  code: '',
  path: '',
  component: '',
  type: 'menu',
  orderNum: 0,
  icon: '',
  keepAlive: false,
  hidden: false
});
const formRef = ref();
const isRootMenu = ref(true);

// 表单验证规则
const formRules = {
  name: [
    { required: true, message: '请输入菜单名称', trigger: 'blur' },
    { min: 2, max: 20, message: '菜单名称长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择菜单类型', trigger: 'change' }
  ],
  orderNum: [
    { required: true, message: '请输入排序', trigger: 'change' }
  ]
};

// 获取菜单类型标签样式
const getMenuTypeTag = (type) => {
  const typeMap = {
    menu: 'primary',
    page: 'success',
    button: 'info'
  };
  return typeMap[type] || 'default';
};

// 获取菜单类型名称
const getMenuTypeName = (type) => {
  const typeMap = {
    menu: '目录',
    page: '菜单',
    button: '按钮'
  };
  return typeMap[type] || '未知';
};

// 加载菜单列表
const loadMenuList = async () => {
  try {
    const params = {
      name: searchForm.name
    };
    const res = await menuApi.getList(params);
    menuList.value = res.data || [];
  } catch (error) {
    console.error('[菜单管理] 加载菜单列表失败:', error);
  }
};

// 加载菜单树（用于选择上级菜单）
const loadMenuTree = async () => {
  try {
    const res = await menuApi.getTree();
    menuTree.value = res.data || [];
  } catch (error) {
    console.error('[菜单管理] 加载菜单树失败:', error);
  }
};

// 查询菜单
const handleSearch = () => {
  loadMenuList();
};

// 重置搜索
const resetSearch = () => {
  searchForm.name = '';
  loadMenuList();
};

// 新增根菜单
const handleAddRootMenu = () => {
  dialogTitle.value = '新增根菜单';
  isRootMenu.value = true;
  // 重置表单数据
  Object.assign(formData, {
    id: '',
    parentId: '0',
    name: '',
    code: '',
    path: '',
    component: '',
    type: 'menu',
    orderNum: 0,
    icon: '',
    keepAlive: false,
    hidden: false
  });
  dialogVisible.value = true;
};

// 新增子菜单
const handleAddSubMenu = (menu) => {
  dialogTitle.value = '新增子菜单';
  isRootMenu.value = false;
  // 重置表单数据
  Object.assign(formData, {
    id: '',
    parentId: menu.id,
    name: '',
    code: '',
    path: '',
    component: '',
    type: 'menu',
    orderNum: 0,
    icon: '',
    keepAlive: false,
    hidden: false
  });
  dialogVisible.value = true;
};

// 编辑菜单
const handleEditMenu = (menu) => {
  dialogTitle.value = '编辑菜单';
  isRootMenu.value = menu.parentId === '0' || menu.parentId === 0;
  // 复制菜单数据到表单
  Object.assign(formData, {
    id: menu.id,
    parentId: menu.parentId || '0',
    name: menu.name,
    code: menu.code,
    path: menu.path,
    component: menu.component,
    type: menu.type,
    orderNum: menu.orderNum || 0,
    icon: menu.icon || '',
    keepAlive: menu.keepAlive || false,
    hidden: menu.hidden || false
  });
  dialogVisible.value = true;
};

// 删除菜单
const handleDeleteMenu = async (menuId) => {
  try {
    // 检查是否有子菜单
    const hasChildren = await menuApi.hasChildren(menuId);
    if (hasChildren.data) {
      ElMessage.warning('该菜单下有子菜单，不能删除');
      return;
    }
    
    await ElMessageBox.confirm('确定要删除该菜单吗？', '确认对话框', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    
    await menuApi.delete(menuId);
    ElMessage.success('删除成功');
    loadMenuList();
    loadMenuTree();
  } catch (error) {
    if (error !== 'cancel') {
      console.error('[菜单管理] 删除菜单失败:', error);
      ElMessage.error('删除失败');
    }
  }
};

// 检查是否是自身或后代节点（避免循环引用）
const isSelfOrDescendant = (node, currentId) => {
  if (!currentId) return false;
  if (node.id === currentId) return true;
  if (node.children) {
    return node.children.some(child => isSelfOrDescendant(child, currentId));
  }
  return false;
};

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate();
    
    const data = {
      ...formData
    };
    
    // 处理空值
    if (!data.code) delete data.code;
    if (!data.path) delete data.path;
    if (!data.component) delete data.component;
    if (!data.icon) delete data.icon;
    
    // 检查菜单名称是否已存在
    const checkRes = await menuApi.checkName(data.name, data.id, data.parentId);
    if (checkRes.data) {
      ElMessage.error('该菜单名称已存在');
      return;
    }
    
    if (data.id) {
      // 更新菜单
      await menuApi.update(data);
      ElMessage.success('更新成功');
    } else {
      // 新增菜单
      await menuApi.save(data);
      ElMessage.success('新增成功');
    }
    
    // 关闭对话框
    dialogVisible.value = false;
    // 重新加载菜单列表和菜单树
    loadMenuList();
    loadMenuTree();
  } catch (error) {
    console.error('[菜单管理] 提交表单失败:', error);
    if (error !== 'cancel') {
      ElMessage.error('提交失败');
    }
  }
};

// 生命周期钩子
onMounted(() => {
  loadMenuList();
  loadMenuTree();
});
</script>

<style scoped>
.menu-management {
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
</style>