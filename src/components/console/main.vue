<template>
  <JwtInspectionVue ref="jwtInspectionRef"></JwtInspectionVue>
  <div class="consloe">
    <div class="menu">
      <el-button
        type="primary"
        :icon="IconMenu"
        @click="handleMenuCollapse"
        plain
      />
      <el-menu
        :default-active="currentActive"
        :collapse="isCollapse"
        @select="handleMenuSelect"
        style="height: 100%"
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
      <div v-if="currentActive === `1-1`" class="blogs">
        <BlogsVue></BlogsVue>
      </div>
      <div v-if="currentActive === `1-2`" class="blog-create">
        <BlogCreateVue></BlogCreateVue>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { Menu as IconMenu, Postcard, Setting } from "@element-plus/icons-vue";

// 检验jwt
import JwtInspectionVue from "@/components/public/util/JwtInspection.vue";

import BlogsVue from "@/components/console/page/blogs/blogs.vue";
import BlogCreateVue from "./page/blogs-create/BlogCreate.vue";
import { useRouter } from "vue-router";

const isCollapse = ref(true);
const currentActive = ref("1-1");

const router = useRouter();
const jwtInspectionRef = ref(null);

function handleMenuCollapse() {
  isCollapse.value = !isCollapse.value;
}

const handleMenuSelect = (key, keyPath) => {
  if (key) {
    currentActive.value = key;
  }
};

onMounted(async () => {
  if (jwtInspectionRef.value) {
    const result = await jwtInspectionRef.value.inspection();
    if (result == false) {
      jwtInspectionRef.value.showAlert({
        title: "错误！",
        type: "error",
        description: "未登录！",
      });
      setTimeout(() => {
        router.push("/");
      }, 1000);
    }
  }
});
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
  background-color: #ffffffb0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.blogs {
  width: 100%;
  height: 95vh;
  background-color: #ffffffa5;
  padding: 20px;
}
.blog-create {
  width: 100%;
  height: 95vh;
  background-color: #ffffffa5;
  padding: 20px;
}
</style>