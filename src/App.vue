<template>
  <YaoSpineView
    class="spine-view"
  />
  <router-view>
  </router-view>
</template>

<script setup>
import YaoSpineView from './components/public/spine/views/YaoSpineView.vue';
</script>

<style scoped>
.spine-view {
  margin: 0;
  padding: 0;
  width: 270px;
  height: 480px;
  position: fixed;
  bottom: 0;
  left: 0;
  z-index: 10000000000;
}
</style>
