<template>
  <el-container>
    <el-header>
      <el-affix :offset="0">
        <headerVue></headerVue>
      </el-affix>
    </el-header>
    <el-main>
      <Carousel></Carousel>
      <div class="main-body">
        <mainVue></mainVue>
      </div>
    </el-main>
    <el-footer>
      <footerVue></footerVue>
    </el-footer>
  </el-container>
</template>

<script setup>
import footerVue from "./components/home/footer.vue";
import headerVue from "./components/home/header.vue";
import mainVue from "./components/home/main.vue";
import Carousel from "./components/home/carousel.vue";
</script>

<style scoped>
.el-header,
.el-footer,
.el-main  {
  margin: 0;
  padding: 0;
}
.el-main .main-body {
  padding: 0;
  margin: 0 140px 0 140px;
}

@media (max-width: 468px) {
  .el-main .main-body {
    margin: 0 20px 0 20px;
  }
}
</style>