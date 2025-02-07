<template>
  <FindVue ref="findRef" />
  <MainVue :blog="blogRef" />
</template>
  
<script setup>
import { onMounted, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import FindVue from "../components/blog/requests/find.vue";
import MainVue from "@/components/blog/main.vue";

const findRef = ref(null);
const blogRef = ref({
  title: "null",
  author: "null",
  authorAvatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
  publishDate: "null",
  content: "null",
  category: "null",
  tags: ['null'],
});

const router = useRouter();
const route = useRoute();

onMounted(async () => {
  const id = route.query.id;
  if (id === undefined) {
    return router.push("/");
  }
  const response = await findRef.value.findRequest({
    id: id
  });
  if (!response) {
    return router.push("/");
  }
  blogRef.value.author = response.author;
  blogRef.value.title = response.title;
  blogRef.value.content = response.content;
  blogRef.value.publishDate = new Date(response.createDate);
});
</script>

<style>
</style>