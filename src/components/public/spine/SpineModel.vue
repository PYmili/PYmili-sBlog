<template>
  <div
    class="spineView"
    v-if="IsSpineViewShow"
    :style="viewStyle"
    @click="handleSpineClick"
    @contextmenu.prevent="handleSpineRightClick"
  >
    <!-- 菜单 -->
    <el-drawer
      v-model="spineMenuModel"
      :title="`${props.spineName}-模型菜单`"
      direction="ltr"
      class="spine-menu"
    >
      <div class="spine-menu-__content">
        <div class="change-animation">
          <h3>animation - 动画</h3>
          <el-radio-group v-model="currentAnimationModel" @change="changeAnimationModel">
            <el-radio v-for="(item, id) in animations" :key="id" 
              :value="item.name">{{ item.name }}</el-radio>
          </el-radio-group>
        </div>
      </div>
      <div class="spine-menu__footer">
        <div class="close-button">
          <el-button 
            v-if="player !== null" 
            type="primary"
            @click="handleCloseSpineClick"
          >关闭模型</el-button>
        </div>
      </div>
    </el-drawer>
    <!-- Spine 动画容器 -->
    <div ref="spineContainer" class="spine-container"></div>
  </div>
  <!-- 音频播放器 -->
  <SpineAudioPlayer 
    ref="spineAudioPlayer"
    :audio-url="spineCurrentAudioUrl"
  />
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick } from "vue";
import {
  SpinePlayer,
  type SpinePlayerConfig,
} from "@esotericsoftware/spine-player";
import SpineAudioPlayer from "@/components/public/spine/SpineAudioPlayer.vue";

// 定义变量
const IsSpineViewShow = ref(true);
const spineMenuModel = ref(false);
const currentAnimationModel = ref("tuoyi1");

// props emits
const props = defineProps({
  jsonUrl: String,
  atlasUrl: String,
  backgroundImageUrl: String,
  spineAudioList: Array,
  spineName: {
    type: String,
    default: "spine",
  },

  // pc pad
  pcPadTop: { type: String, default: "0%" },
  pcPadBottom: { type: String, default: "0%" },
  pcPadLeft: { type: String, default: "0%" },
  pcPadRight: { type: String, default: "0%" },

  // Mobile pad
  mobilePadTop: { type: String, default: "0%" },
  mobilePadBottom: { type: String, default: "0%" },
  mobilePadLeft: { type: String, default: "0%" },
  mobilePadRight: { type: String, default: "0%" },
});
const emits = defineEmits(["close-view"]);
// console.log(jsonUrl, atlasUrl, backgroundImageUrl);

// spine audio player
const spineAudioPlayer = ref(false);
const spineCurrentAudioUrl = ref(props.spineAudioList[0].url);
const changeSpineCurrentAudioUrl = (type: String) => {
  props.spineAudioList.map((item) => {
    if (item.type === type) {
      spineCurrentAudioUrl.value = item.url;
    }
  });
};

// 定义事件触发
/**
 * 处理左键点击事件
 * @param e
 */
const handleSpineClick = async (e) => {
  console.log(e.x, e.y);

  // chest
  if ((e.x < 170 && e.x > 96) && (e.y < 505 && e.y > 466)) {
    changeSpineCurrentAudioUrl('chest');
  }
  // body
  if ((e.x < 170 && e.x > 100) && (e.y < 560 && e.y > 510)) {
    changeSpineCurrentAudioUrl('body');;
  }
  // face
  if ((e.x < 157 && e.x > 120) && (e.y < 428 && e.y > 404)) {
    changeSpineCurrentAudioUrl('face');
  }
  // head
  if ((e.x < 170 && e.x > 100) && (e.y < 393 && e.y > 136)) {
    changeSpineCurrentAudioUrl('head');
  }
  // foot
  if ((e.x < 180 && e.x > 84) && (e.y < 739 && e.y > 620)) {
    changeSpineCurrentAudioUrl('foot');
  }
  await nextTick(() => {
    spineAudioPlayer.value.togglePlay();
  });
  changeSpineCurrentAudioUrl('default');
};

/**
 * 处理右键菜单事件
 * @param e
 */
const handleSpineRightClick = (e) => {
  spineMenuModel.value = true;
};

/**
 * 处理Animation模型选择
 * @param data 
 */
const changeAnimationModel = (data) => {
  player.setAnimation(data);
};

const handleCloseSpineClick = () => {
  if (player) {
    player?.dispose();
    player = null;
  }
  // 关闭显示
  IsSpineViewShow.value = false;
  emits("close-view");
};

// 定义容器引用
const spineContainer = ref<HTMLDivElement | null>(null);

// 定义 SpinePlayer 实例
let player: SpinePlayer | null = null;

// 定义视口配置
const viewStyle = ref({
  width: "100%",
  height: "100%",
});

// spine获取的变量
const animations = ref([]);

// 动态生成 SpinePlayerConfig
const generateConfig = (isMobile: boolean): SpinePlayerConfig => {
  const desktopViewportConfig = {
    debugRender: false,
    x: 0,
    y: 0,
    padTop: props.pcPadTop, // pc端自定义值
    padBottom: props.pcPadBottom, // pc端自定义值
    padLeft: props.pcPadLeft,
    padRight: props.pcPadRight,
  };

  const mobileViewportConfig = {
    debugRender: false,
    x: 0,
    y: 0,
    padTop: props.mobilePadTop, // 移动端自定义值
    padBottom: props.mobilePadBottom, // 移动端自定义值
    padLeft: props.mobilePadLeft,
    padRight: props.mobilePadRight,
  };

  return {
    jsonUrl: props.jsonUrl,
    atlasUrl: props.atlasUrl,
    backgroundImage: {
      url: props.backgroundImageUrl,
      x: 0,
      y: 0,
    },
    alpha: true,
    premultipliedAlpha: true,
    backgroundColor: "#00000000",
    preserveDrawingBuffer: false,
    showControls: false,
    showLoading: true,
    defaultMix: 0.25,
    animation: currentAnimationModel.value,
    skin: "default",
    viewport: isMobile ? mobileViewportConfig : desktopViewportConfig,
    success: (instance) => {
      player = instance
      // player.animationState?.addListener({
      //   complete: (entry) => {
      //     console.log("Animation completed:", entry);
      //   },
      // });
      // 获取所有animations
      animations.value = player.animationState.data.skeletonData.animations;
    },
    error: (reason) => {
      console.error("Spine animation load error:", reason);
    },
  };
};

// 初始化 SpinePlayer
onMounted(() => {
  if (spineContainer.value) {
    player = new SpinePlayer(
      spineContainer.value,
      generateConfig(window.innerWidth < 768)
    );
  }
});

// 监听窗口大小变化
let resizeTimeout: number | undefined;

window.addEventListener("resize", () => {
  clearTimeout(resizeTimeout);
  resizeTimeout = window.setTimeout(() => {
    if (spineContainer.value) {
      player?.dispose();
      player = new SpinePlayer(
        spineContainer.value,
        generateConfig(window.innerWidth < 768)
      );
    }
  }, 200);
});

// 组件卸载时销毁 SpinePlayer
onBeforeUnmount(() => {
  player?.dispose();
  player = null;
});
</script>
  
<style scoped>
.spineView {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: transparent;
}

.spine-container {
  width: 100%;
  height: 100%;
  background: transparent;
  /* background: #000; */
}

.spine-menu {
  position: relative;
  background-color: #fff;
}

.spine-menu .spine-menu__footer {
  position: absolute;
  bottom: 10px;
  right: 10px;
}
</style>