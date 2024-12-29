<template>
  <LoginRequestEventVue ref="loginEventRef"></LoginRequestEventVue>
  <el-container class="container">
    <MainVue
      @tiggerUserLogin="handleUserLogin"
    ></MainVue>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import MainVue from '@/components/login/main.vue'
import LoginRequestEventVue from '@/components/login/event/LoginRequestEvent.vue';

const loginEventRef = ref(null)

function handleUserLogin(params) {
  if (loginEventRef.value) {
    loginEventRef.value.handleUserLogin(params)
  }
} 
</script>

<style scoped>
  .container {
    margin: 0;
    padding: 0;
    height: 100vh;
    width: 100%;
  }
</style>