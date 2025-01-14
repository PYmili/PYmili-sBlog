<template>
  <!-- 轮播图 -->
  <div class="carousel">
    <div class="carousel-content">
      <el-carousel indicator-position="outside" height="600px">
        <el-carousel-item v-for="item in carouselArray" :key="item">
          <img :src="item" :alt="item" justify="center" />
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

let carouselArray = ref([
  `${import.meta.env.BASE_URL}images/main/carousel-1.jpg`,
  `${import.meta.env.BASE_URL}images/main/carousel-2.jpg`,
  `${import.meta.env.BASE_URL}images/main/carousel-3.png`,
  `${import.meta.env.BASE_URL}images/main/carousel-4.jpg`,
  `${import.meta.env.BASE_URL}images/main/carousel-5.jpg`,
  `${import.meta.env.BASE_URL}images/main/carousel-6.jpg`,
]);
</script>

<style scoped>
.carousel {
  width: 100%;
  background-color: #b7b7b788;
  display: flex;
  justify-content: center;
  align-items: center;
}
.carousel .carousel-content {
  width: 100%;
}
.carousel img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.me {
  background-color: #000000bf;
  color: white;
}

@media (max-width: 468px) {
  .el-carousel {
    height: 250px;
  }
  .carousel img {
    height: 250px;
    object-fit: cover;
  }
}
</style>