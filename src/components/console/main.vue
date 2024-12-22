<template>
  <div class="consloe">
    <div class="menu">
        <el-button 
            type="primary" 
            :icon="IconMenu" 
            @click="handleMenuCollapse"
            plain  
        />
        <el-menu 
            default-active="1-1"
            :collapse="isCollapse"
            style="height: 100%;"
        >
            <el-sub-menu index="1">
                <template #title>
                    <el-icon><Postcard /></el-icon>
                    <span>博客</span>
                </template>
                <el-menu-item-group>
                    <template #title><span>文章</span></template>
                    <el-menu-item index="1-1">查看</el-menu-item>
                    <el-menu-item index="1-2">创建</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="Group Two">
                    <template #title><span>流量</span></template>
                    <el-menu-item index="1-3">网站</el-menu-item>
                    <el-menu-item index="1-3">文章</el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>
            <el-menu-item index="2">
                <el-icon><setting /></el-icon>
                <template #title>设置</template>
            </el-menu-item>    
        </el-menu>
    </div>
    <div class="content">
        
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue"
import {
  Menu as IconMenu,
  Postcard,
  Setting,
} from '@element-plus/icons-vue'

const isCollapse = ref(true)

function handleMenuCollapse() {
    isCollapse.value = !isCollapse.value
}

</script>

<style scoped>
.consloe {
  display: flex;
  width: 100%;
  height: 100vh;
  background-color: #000000;
}
.consloe .menu {
    display: flex;
    flex-direction: column;
}
.content {
    width: 100%;
    height: 100vh;
    background-color: #ffffff82;
}
</style>