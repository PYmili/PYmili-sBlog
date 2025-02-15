<template>
  <el-container v-if="isSpineContainer">
    <el-main>
      <SpineModel
        :jsonUrl="spineJson"
        :atlasUrl="spineAtlas"
        :spine-audio-list="spineAudioList"
        pc-pad-top="0%"
        pc-pad-bottom="-27%"
        spine-name="肴"
        @close-view="handleCloseView"
      />
    </el-main>
  </el-container>
</template>

<script setup>
import { ref } from "vue";
import SpineModel from "@/components/public/spine/SpineModel.vue";

// 资源配置
const spineJson = "/resource/spine/yao/model/yao.json";
const spineAtlas = "/resource/spine/yao/model/yao.atlas";
const spineAudioList = [
  { type: "default", url: "/resource/spine/yao/audio/1.mp3" },
  { type: "body", url: "/resource/spine/yao/audio/2.mp3" },
  { type: "face", url: "/resource/spine/yao/audio/3.mp3" },
  { type: "chest", url: "/resource/spine/yao/audio/4.mp3" },
  { type: "head", url: "/resource/spine/yao/audio/嘿嘿.mp3" },
  { type: "foot", url: "/resource/spine/yao/audio/郎君赢啦.mp3" },
];

const isSpineContainer = ref(true);

const handleCloseView = () => {
  isSpineContainer.value = false;
}
</script>

<style scoped>
:deep(.el-main) {
  margin: 0;
  padding: 0;
}
</style>