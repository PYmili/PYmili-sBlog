<template>
  <JwtInspection ref="JwtInspectionChild" />
  <el-container>
    <MainVue></MainVue>
  </el-container>
</template>

<script setup>
import { nextTick, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
// components
import MainVue from "@/components/console/main.vue";
import JwtInspection from "@/components/public/util/JwtInspection.vue";
import { ElMessage } from "element-plus";

// all child
const JwtInspectionChild = ref(null);

// use static
const router = useRouter();

onMounted(async () => {
  // 验证jwt
  const isLogin = await nextTick(async () => {
    return await JwtInspectionChild.value.verifyJwt();
  });
  if (isLogin === false) {
    ElMessage({
      type: "warning",
      message: "未登录！",
      showClose: true
    });
    setTimeout(() => {
      router.push("/login")
    }, 2000);
  }
});
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}
</style> 