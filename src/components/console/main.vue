<template>
  <JwtInspectionVue ref="jwtInspectionRef"></JwtInspectionVue>
  <DialogVue ref="dialogVueRef"></DialogVue>
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
        <el-menu-item 
          class="quit-el-menu-item" 
          @click="handleQuitEvent"
        >
          <el-icon>
            <img src="/images/svg/quit.svg" style="width: 15px;">
          </el-icon>
          <template #title>退出</template>
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
import { onMounted, ref, nextTick } from "vue";
import { 
  Menu as IconMenu,
  Postcard, 
  Setting,
} from "@element-plus/icons-vue";

// router
import { useRouter } from "vue-router";
import { useCookies } from "vue3-cookies";

// 检验jwt
import JwtInspectionVue from "@/components/public/util/JwtInspection.vue";
import BlogsVue from "@/components/console/page/blogs/blogs.vue";
import BlogCreateVue from "./page/blogs-create/BlogCreate.vue";
import DialogVue from "@/components/public/element-plus/Dialog.vue";

const isCollapse = ref(true);
const currentActive = ref("1-1");

const router = useRouter();
const { cookies } = useCookies();
const jwtInspectionRef = ref(null);
const dialogVueRef = ref(null);

function handleQuitEvent() {
  console.log("handle quit event.");
  let handleConfirm = () => {
    cookies.remove("jwt");
    cookies.remove("username");
    router.push("/");
  }
  nextTick(() => {
    dialogVueRef.value.init({
      title: "提示",
      message: "您确定要退出吗？此操作将导致登录被清除。",
      handleConfirm: handleConfirm
    });
    dialogVueRef.value.isShow(true);
  });
}

function handleMenuCollapse() {
  isCollapse.value = !isCollapse.valuecancel;
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
.quit-el-menu-item {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
}
</style>