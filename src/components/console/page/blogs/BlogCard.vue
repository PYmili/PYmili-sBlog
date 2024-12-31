<template>
  <div class="blogs-grid">
    <el-card v-for="item in blogs" :key="item.id">
      <template #header>
        <span style="width: 100%; text-align: center">{{ item.title }}</span>
      </template>
      <div class="content">
        <div class="card-img">
          <img v-if="item.topImg != null" :src="item.topImg" alt="" />
          <img v-else src="/images/svg/blog.svg" alt="" />
        </div>
        {{ item.content }}
      </div>
      <template #footer>
        <div class="link">
          <a href="#">{{ item.introduction }}</a>
        </div>
      </template>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useCookies } from "vue3-cookies";

// cookies
const { cookies } = useCookies();

const blogs = ref([]);
const api_url = `${import.meta.env.VITE_API_HOST}/blogs/find_all`

async function requestBlogs() {
  const data = {
    author: cookies.get("username"),
    jwt: cookies.get("jwt"),
  };
  await axios
    .post(api_url, data, {
      headers: {
        "Content-Type": "application/json",
      },
    })
    .then((response) => {
      if (response.data.code == 200) {
        blogs.value = response.data.data;
      }
    })
    .catch((error) => {});
}

onMounted(async () => {
  await requestBlogs();
});
</script>

<style scoped>
.blogs-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(25%, 1fr));
  grid-column-gap: 10px;
}
.blogs-grid .content {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.blogs-grid .content img {
  width: 30%;
}
</style>