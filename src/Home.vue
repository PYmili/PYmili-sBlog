<template>
    <el-container>
        <el-header>
            <el-affix :offset="0">
                <headerVue></headerVue>
            </el-affix>
        </el-header>
        <el-main>
            <mainVue></mainVue>
        </el-main>
        <el-footer>
            <footerVue></footerVue>
        </el-footer>
  </el-container>
</template>

<script setup>
import footerVue from "./components/home/footer.vue"
import headerVue from "./components/home/header.vue"
import mainVue from "./components/home/main.vue"
import LoginVue from './Login.vue'
</script>

<style scoped>
  * {
    margin: 0;
    padding: 0;
  }
</style>