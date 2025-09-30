<!-- src/components/layout/MenuList.vue -->
<template>
  <ul class="menu-list">
    <li
        v-for="item in items"
        :key="item.path"
        class="menu-item"
        :class="{ active: isActive(item) }"
    >
      <!-- 有子菜单 -->
      <template v-if="item.children && item.children.length > 0">
        <div class="menu-title" @click="toggle(item)">
          <!-- <span class="menu-icon" v-if="item.meta?.icon">
            {{ item.meta?.icon }}
          </span> -->
          <span class="menu-text">{{ item.meta?.title }}</span>
          <span class="arrow">{{ isExpanded(item) ? '▼' : '▶' }}</span>
        </div>
        <transition name="slide">
          <menu-list
              v-show="isExpanded(item)"
              :items="item.children"
              class="submenu"
          />
        </transition>
      </template>

      <!-- 无子菜单（叶子节点） -->
      <template v-else>
        <router-link :to="item.path" class="menu-link">
          <!-- <span class="menu-icon" v-if="item.meta?.icon">
            {{ item.meta?.icon }}
          </span> -->
          <span class="menu-text">{{ item.meta?.title }}</span>
        </router-link>
      </template>
    </li>
  </ul>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const expandedKeys = ref<Set<string>>(new Set())

const props = defineProps<{
  items: any[]
}>()

const isActive = (item: any) => {
  return route.path === item.path
}

const isExpanded = (item: any) => {
  return expandedKeys.value.has(item.path)
}

const toggle = (item: any) => {
  const key = item.path
  if (expandedKeys.value.has(key)) {
    expandedKeys.value.delete(key)
  } else {
    expandedKeys.value.add(key)
  }
}
</script>

<style scoped>
.menu-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.menu-item {
  border-bottom: 1px solid #f0f0f0;
}

.menu-title,
.menu-link {
  display: flex;
  align-items: center;
  padding: 12px 20px;
  cursor: pointer;
  text-decoration: none;
  color: #333;
  transition: background 0.3s;
}

.menu-title:hover,
.menu-link:hover {
  background-color: #f5f5f5;
}

.active {
  background-color: #e6f7ff !important;
  color: #1890ff;
}

.menu-icon {
  margin-right: 8px;
  width: 16px;
  text-align: center;
}

.arrow {
  margin-left: auto;
  font-size: 12px;
}

.submenu {
  padding-left: 20px;
}

.slide-enter-active,
.slide-leave-active {
  transition: max-height 0.3s ease;
  overflow: hidden;
  max-height: 1000px;
}

.slide-enter-from,
.slide-leave-to {
  max-height: 0;
}
</style>