<template>
    <!-- 轮播图 -->
    <div class="carousel">
        <el-carousel indicator-position="outside" height="900px">
            <el-carousel-item 
                v-for="item in carouselArray"
                :key="item">
                <img 
                    :src="item" 
                    :alt="item" 
                    justify="center">
            </el-carousel-item>
        </el-carousel>
    </div>
    <!-- 关于我 -->
    <div class="me">
        <el-tabs type="border-card">
            <el-tab-pane label="开源">
                <template #label>
                    <el-icon><img src="/images/main/svg/github.svg" alt="github" width="20px"></el-icon>
                    <span style="margin-left: 10px;">github</span>
                </template>
                <!-- 开源列表 -->
                <GithubProjectsVue></GithubProjectsVue>
            </el-tab-pane>
            <el-tab-pane label="平台账号">
                <template #label>
                    <el-icon><Operation /></el-icon>
                    <span style="margin-left: 10px;">平台账号</span>
                </template>
                <!-- 各大平台的账号 -->
                <PlatformAccountVue></PlatformAccountVue>
            </el-tab-pane>
            <el-tab-pane label="目标">
                <template #label>
                    <el-icon><Operation /></el-icon>
                    <span style="margin-left: 10px;">目标</span>
                </template>
            </el-tab-pane>
            <el-tab-pane label="计划">
                <template #label>
                    <el-icon><Operation /></el-icon>
                    <span style="margin-left: 10px;">计划</span>
                </template>
            </el-tab-pane>
        </el-tabs>
    </div>
    <!-- 人生流程图 -->
    <div class="my-life">
        <MyLifeVue></MyLifeVue>
    </div>
</template>

<script setup>
import { ref } from "vue"
import { Operation } from "@element-plus/icons-vue"
import GithubProjectsVue from "@/components/home/main/GithubProject.vue"
import PlatformAccountVue from "@/components/home/main/PlatformAccount.vue";
import MyLifeVue from "@/components/home/main/MyLife.vue"

let carouselArray = ref([
    import.meta.env.BASE_URL + "/images/main/carousel-1.jpg",
    import.meta.env.BASE_URL + "/images/main/carousel-2.jpg",
    import.meta.env.BASE_URL + "/images/main/carousel-3.png",
    import.meta.env.BASE_URL + "/images/main/carousel-4.jpg",
    import.meta.env.BASE_URL + "/images/main/carousel-5.jpg",
    import.meta.env.BASE_URL +  "/images/main/carousel-6.jpg",
])

</script>

<style scoped>
    .carousel {
        background-color: #b7b7b788;
    }
    .carousel img {
        width: 100%;
        height: 100%;
    }
    .me {
        background-color: #000000bf;
        color: white;
    }
</style>