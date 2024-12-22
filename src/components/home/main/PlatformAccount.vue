<template>
    <el-card>
        <template #header>
            <span>各平台的视频账号</span>
        </template>
        <ul style="list-style-type: none;">
            <li v-for="(item) in accounts" :key="item.id">
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

let accounts = ref([
    {
        id: 1,
        title: "bilibili - PYmili",
        url: "https://space.bilibili.com/483109517?spm_id_from=333.1007.0.0"
    },
    {
        id: 2,
        title: "快手 - PYmili",
        url: "https://www.baidu.com"
    }
])

</script>

<style scoped>
    .link {
        text-align: center;
    }
</style>