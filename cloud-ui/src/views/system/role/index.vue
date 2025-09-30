<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { roleList } from '@/api/system/role';
interface role {
    id: number;
    roleName: string;
    roleCode: string;
    description: string;
    status: number;
    createTime: string;
    updateTime: string;
}
const roleData = ref<role[]>([])

const handleEdit = (id: number) => {
    console.log('编辑权限', id);
}

const handleDelete = (id: number) => {
    console.log('删除权限', id);
}  
onMounted(() =>{
    roleList().then(res => {
        if(res.code === 200) {
            roleData.value = res.data;
        }
    })
})
</script>

<template>
    <div>
        <el-table :data="roleData">
            <el-table-column prop="roleName" label="权限名称"></el-table-column>
            <el-table-column prop="roleCode" label="权限编码"></el-table-column>
            <el-table-column prop="description" label="权限描述"></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column prop="updateTime" label="更新时间"></el-table-column>
            <el-table-column label="操作">
                <template #default="{row}">
                    <el-button type="text" size="mini" @click="handleEdit(row.id)">编辑</el-button>
                    <el-button type="text" size="mini" @click="handleDelete(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<style scoped>



</style>