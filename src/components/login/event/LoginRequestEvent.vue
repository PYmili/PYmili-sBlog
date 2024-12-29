<template>
    <AlertVue ref="AlertVueRef"></AlertVue>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
// vue3 cookies
import { useCookies } from "vue3-cookies"
// 路由
import { useRouter } from 'vue-router'

import AlertVue from '@/components/public/alert.vue'

// alert vue ref
const AlertVueRef = ref(null)

function showAlert(params) {
  if (AlertVueRef.value) {
    AlertVueRef.value.showAlert(params)
  }
}

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
      if (response.data.code !== 200) {
        return
      }
      showAlert({
        title: '成功！',
        type: 'success',
        description: '登录成功！'
      })
      // 写入cookies和username
      cookies.set('jwt', response.data.data, '1d')
      cookies.set('username', params.value.username, '1d')
      // 登录成功跳转
      setTimeout(() => {
        router.push('/console')
      }, 3000)
    })
    .catch(error => {
      if (error.response) {
        showAlert({
          title: '失败！',
          type: 'warning',
          description: JSON.parse(error.request.response).data
        })
      } else if (error.request) {
        showAlert({
          title: '错误！',
          type: 'error',
          description: '服务器未响应！'
        })
      } else {
        showAlert({
          title: '错误！',
          type: 'error',
          description: '发送请求失败！'
        })
      }
    })
}

defineExpose({
    handleUserLogin
})
</script>

<style>

</style>