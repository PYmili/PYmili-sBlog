<template>
  <el-menu
    mode="horizontal"
    :ellipsis="false"
    :default-active="selectedIndex"
    @select="handleSelect"
    background-color="#585b5f"
    text-color="#ffffff"
    active-text-color="#ffffff"
  >
    <el-tooltip
      class="box-item"
      effect="dark"
      content="主页"
      placement="bottom"
    >
      <router-link to="/">
        <el-menu-item index="0">
          <img style="width: 50px" src="/favicon.svg" alt="logo" />
        </el-menu-item>
      </router-link>
    </el-tooltip>
    <el-tooltip
      class="box-item"
      effect="dark"
      content="前往博客站点"
      placement="bottom"
    >
      <el-menu-item index="1">博客中心</el-menu-item>
    </el-tooltip>
    <el-sub-menu index="2">
      <template #title>其他</template>
      <el-menu-item index="2-1">开源</el-menu-item>
      <el-menu-item index="2-2">资源</el-menu-item>
      <el-menu-item index="2-3">社区</el-menu-item>
      <el-sub-menu index="2-4">
        <template #title>其他</template>
        <el-menu-item @click="toLink(`/login`)" index="2-4-1">登录后台</el-menu-item>
        <!-- <el-menu-item index="2-4-2"></el-menu-item>
                <el-menu-item index="2-4-3"></el-menu-item> -->
      </el-sub-menu>
    </el-sub-menu>
    <el-tooltip
      class="box-item"
      effect="dark"
      content="输入你想查询的文章后回车"
      placement="bottom"
    >
      <div class="search-input">
        <el-input
          v-model="searchInputModel"
          size="large"
          placeholder="请输入..."
          :suffix-icon="Search"
        />
      </div>
    </el-tooltip>
  </el-menu>
</template>

<script lang="ts" setup>
import { Search } from "@element-plus/icons-vue";
import { ref } from "vue";
import { useRouter } from "vue-router";

const selectedIndex = ref("0");
const searchInputModel = ref(null);

// use router
const router = useRouter();


// 通过router路由到指定地址
function toLink(link: string) {
    if (link && router) {
        router.push(link);
    }
}


// 导航栏选中回调
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>
  
<style scoped>
.nav {
  /* background-color="#585b5f" */
  --el-menu-bg-color: #585b5f;
  /* text-color="#fff" */
  --el-menu-text-color: #ffffff;
  /* active-text-color="#000" */
  --el-menu-active-color: #000000;
}
.el-menu--horizontal > .el-menu-item:nth-child(1) {
  margin-right: 10px;
}
.search-input {
  position: absolute;
  top: 15%;
  right: 35px;
  width: 20%;
  /* align-content: center; */
}
</style>