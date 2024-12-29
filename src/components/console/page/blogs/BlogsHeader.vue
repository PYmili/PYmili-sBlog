<template>
  <div class="flex items-center">
    <div class="avatar">
      <el-avatar v-if="avatar_url" :size="64" :url="UserFilled" class="mr-3" />
      <el-avatar v-else :size="64" :icon="UserFilled" />
    </div>
  </div>
</template>

<script setup>
import { UserFilled } from "@element-plus/icons-vue";
import { ref } from "vue";

const avatar_url = ref(null);
</script>

<style scoped>
.avatar {
  margin-bottom: 10px;
}
</style>