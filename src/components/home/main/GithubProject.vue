<template>
    <el-card>
        <template #header>
            <span>开源项目</span>
        </template>
        <ul style="list-style-type: none;">
            <li v-for="(item) in githubProjects" :key="item.id">
                <el-card shadow="hover" style="margin-bottom: 10px;">
                    <div class="link">
                        <el-link :href="item.url" type="primary">{{ item.title }}</el-link>
                    </div>
                </el-card>
            </li>
        </ul>
    </el-card>
</template>

<script setup>
import { ref } from "vue"
let githubProjects = ref([
    { 
        id: 1,
        title: "MyFlowingFireflyWife - Python实现的流萤宠物",
        url: "https://github.com/PYmili/MyFlowingFireflyWife"
    },
    {
        id: 2,
        title: "Mili-Wallpaper - Python实现的桌面动态壁纸",
        url: "https://github.com/PYmili/Mili_Wallpaper"
    }
])

</script>

<style scoped>
    .link {
        text-align: center;
    }
</style>