<template>
  <AlertVue ref="AlertChild" />
</template>

<script setup>
import { nextTick, ref } from "vue";
import axios from "axios";
// vue3 cookies
import { useCookies } from "vue3-cookies";
// 路由
import { useRouter } from "vue-router";
// hash
import CryptoJS from "crypto-js";

import AlertVue from "@/components/public/alert.vue";

// all child
const AlertChild = ref(null);

// cookies
const { cookies } = useCookies();
// router
const router = useRouter();
// axios create
const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_HOST,
  // withCredentials: true,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

function hashPassword(pwd) {
  return CryptoJS.SHA256(pwd).toString();
}

async function showAlert(params) {
  return await nextTick(async () => {
    AlertChild.value.showAlert(params);
  });
}

// 用户登录事件
async function handleUserLogin(params) {
  const data = JSON.stringify({
    name: params.value.username,
    passwordHash: hashPassword(params.value.password),
  });

  await apiClient
    .post("/user/login", data)
    .then((response) => {
      showAlert({
        title: "成功！",
        type: "success",
        description: "登录成功！",
      });
      // 保存jwt
      cookies.set("jwt", response.data.data, "1d");
      // 登录成功跳转
      setTimeout(() => {
        router.push("/console");
      }, 1000);
    })
    .catch((error) => {
      if (error.response) {
        showAlert({
          title: "失败！",
          type: "warning",
          description: JSON.parse(error.request.response).data,
        });
      } else if (error.request) {
        showAlert({
          title: "错误！",
          type: "error",
          description: "服务器未响应！",
        });
      } else {
        showAlert({
          title: "错误！",
          type: "error",
          description: "发送请求失败！",
        });
      }
    });
}

defineExpose({
  handleUserLogin
});
</script>