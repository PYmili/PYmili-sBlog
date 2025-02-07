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
          <el-button @click="handleToLinkEvent(`/blog?id=${item.id}`)">{{ item.introduction }}</el-button>
        </div>
      </template>
    </el-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useCookies } from "vue3-cookies";
import { useRouter } from "vue-router";

// cookies
const { cookies } = useCookies();

// router
const router = useRouter();

const blogs = ref([]);
const api_url = `${import.meta.env.VITE_API_HOST}/blog/find-range`


function handleToLinkEvent(link) {
  if (router && link) {
    router.push(link);
  }
}

async function requestBlogs() {
  const jwt = cookies.get("jwt");
  const start = 0;
  const number = 10;

  await axios.post(api_url + `?start=${start}&number=${number}`, {}, {
      headers: {
        "Content-Type": "application/json",
        "Authentication": `Bearer ${jwt}`
      },
    })
    .then((response) => {
      blogs.value = response.data.data;
    })
    .catch((error) => {
      console.log(error.request.response);
    });
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