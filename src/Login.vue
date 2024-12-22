<template>
  <el-alert
    v-if="isShowAlert"
    :title="alertTitle"
    :type="alertType"
    :description="alertDescription"
    show-icon
    class="tip-alert"
    @close="isShowAlert = false"
  />
  <el-container class="container">
    <MainVue
      @tiggerUserLogin="handleUserLogin"
    ></MainVue>
  </el-container>
</template>

<script setup>
import MainVue from '@/components/login/main.vue'
import axios from 'axios';
import { ref } from "vue"
// vue3 cookies
import { useCookies } from "vue3-cookies"
// 路由
import { useRouter } from 'vue-router'

const isShowAlert = ref(false)
const alertTitle = ref("成功！")
const alertType = ref("success")
const alertDescription = ref("")

// cookies
const { cookies } = useCookies()
// router
const router = useRouter()

// 判断是否已登录
function isLogin() {
  if (cookies.get('jwt')) {
    router.push('/console')
  }
}
isLogin()

// 显示提示框
async function showAlert(params) {
  alertTitle.value = params.title
  alertType.value = params.type
  alertDescription.value = params.description
  isShowAlert.value = false
  isShowAlert.value = true
}

// 用户登录事件
async function handleUserLogin(params) {
  // console.log(params.value);
  const data = JSON.stringify({
    name: btoa(params.value.username),
    password: btoa(params.value.password),
  });
  // console.log(data)
  
  await axios.post('http://localhost:8080/user/login', data, {
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then(response => {
      // console.log(response.data);
      if (response.data.code === 200) {
        showAlert({
          title: '成功！',
          type: 'success',
          description: '登录成功！'
        })
        cookies.set('jwt', response.data.data, '1d')
      }
    })
    .catch(error => {
      const response = JSON.parse(error.request.response)
      // console.log(response);
      if (response) {
        showAlert({
          title: '失败！',
          type: 'error',
          description: response.data
        })
      } else {
        showAlert({
          title: '失败！',
          type: 'error',
          description: '请求错误！'
        })
      }
    })
    router.push('/console')
}
</script>

<style>
  .tip-alert {
    position: absolute;
    top: 10px;
    right: 10px;
    width: 300px;
  }
  .container {
    margin: 0;
    padding: 0;
    height: 100vh;
    width: 100%;
  }
</style>