<template>
  <LoginRequestEventVue ref="loginEventChild" />
  <JwtInspection ref="JwtInspectionChild" />
  <el-container class="container">
    <MainVue @tiggerUserLogin="handleUserLogin" />
  </el-container>
</template>

<script setup>
import { nextTick, onMounted, ref } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
// components
import MainVue from "@/components/login/main.vue";
import LoginRequestEventVue from "@/components/login/event/LoginRequestEvent.vue";
import JwtInspection from "@/components/public/util/JwtInspection.vue";

// all child
const loginEventChild = ref(null);
const JwtInspectionChild = ref(null);
// use static
const router = useRouter();

/**
 * 处理用户登录事件
 * @param params request data
 */
async function handleUserLogin(params) {
  return await nextTick(async () => {
    return await loginEventChild.value.handleUserLogin(params);
  });
}

onMounted(async () => {
  // 通过验证jwt查看用户是否登录
  const isLogined = await nextTick(async () => {
    return await JwtInspectionChild.value.verifyJwt();
  });
  if (isLogined == true) {
    ElMessage({
      type: "success",
      message: "已登录！",
      showClose: true,
    });
    router.push("/console");
  }
});
</script>

<style scoped>
.container {
  margin: 0;
  padding: 0;
  height: 100vh;
  width: 100%;
}
</style>