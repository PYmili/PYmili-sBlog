<template></template>

<script setup>
import { onMounted, ref, onUnmounted } from "vue";

// 初始化props
const props = defineProps({
  audioUrl: String,
});

// 原生音频播放
let audio = new Audio(props.audioUrl);
const isPlaying = ref(true);

const togglePlay = (volume) => {
  // console.log("toggle play: " + props.audioUrl);
  // 停止上次播放
  if (audio) {
    audio.pause();
  }
  // 重载audio
  audio = new Audio(props.audioUrl);
  if (isPlaying.value === true) {
    audio.volume = volume ? volume !== undefined : 0.5;
    audio.play().catch((error) => {
      console.error(`播放音频：${props.audioUrl}失败` + "错误信息：" + error);
    });
  } else {
    audio.pause();
  }
};

// 事件处理函数
const handleEnded = () => (isPlaying.value = true);
const handlePlay = () => (isPlaying.value = false);
const handlePause = () => (isPlaying.value = true);

// 注册监听事件
onMounted(() => {
  audio.addEventListener("ended", handleEnded);
  audio.addEventListener("play", handlePlay);
  audio.addEventListener("pause", handlePause);
});

// 清理资源
onUnmounted(() => {
  audio.removeEventListener('ended', handleEnded);
  audio.removeEventListener('play', handlePlay);
  audio.removeEventListener('pause', handlePause);
  audio.pause();
  audio.src = ''; // 释放资源
});

defineExpose({
    togglePlay
});
</script>

<style>
</style>