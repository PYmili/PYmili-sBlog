<template>
  <div class="header">
    <BlogsHeaderVue></BlogsHeaderVue>
  </div>
  <div class="body">
    <ul>
      <li>
        <BlogCardVue></BlogCardVue>
      </li>
    </ul>
  </div>
</template>

<script setup>
import BlogsHeaderVue from "@/components/console/page/blogs/BlogsHeader.vue";
import BlogCardVue from "@/components/console/page/blogs/BlogCard.vue";
</script>

<style scoped>
ul {
  list-style-type: none;
}
.body {
  width: 100%;
}
</style>