<template>
  <div class="gallery-container">
    <el-header>
      <el-input
        v-model="searchQuery"
        placeholder="搜索图片..."
        class="search-input"
      >
        <template #append>
          <el-button
            type="primary"
            @click="handleSearch"
            :icon="Search"
            plain
          />
        </template>
      </el-input>
    </el-header>

    <el-main>
      <el-row :gutter="20">
        <el-col
          v-for="image in filteredImages"
          :key="image.id"
          :xs="24"
          :sm="12"
          :md="8"
          :lg="6"
          :xl="4"
        >
          <el-card
            :body-style="{ padding: '0px' }"
            class="image-card"
            @click="showImageDetails(image)"
          >
            <img :src="image.url" class="image" />
            <div class="image-info">
              <span>{{ image.title }}</span>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>

    <el-dialog
      v-model="dialogVisible"
      :title="selectedImage.title"
      width="80%"
      class="image-dialog"
    >
      <img :src="selectedImage.url" class="dialog-image" />
      <p v-if="selectedImage.link">
        <el-button
          type="primary"
          @click="handleDetailedButton(selectedImage.link)"
          plain
        >
          查看详细
        </el-button>
      </p>
    </el-dialog>
  </div>
</template>
  
<script setup>
import { ref, computed } from "vue";
import { Search } from "@element-plus/icons-vue";
import {
  ElHeader,
  ElMain,
  ElInput,
  ElButton,
  ElRow,
  ElCol,
  ElCard,
  ElDialog,
  ElIcon,
} from "element-plus";
import { useRouter } from "vue-router";

// router
const router = useRouter();

// 模拟图片数据
const images = ref([
  {
    id: 1,
    title: "fw-腐竹",
    url: "/images/gallery/fw-fuzhu.png",
    link: "/fw",
  },
  // 添加更多图片...
]);

const searchQuery = ref("");
const dialogVisible = ref(false);
const selectedImage = ref({});

const filteredImages = computed(() => {
  if (!searchQuery.value) {
    return images.value;
  }
  return images.value.filter((image) =>
    image.title.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const handleSearch = () => {
  console.log("Searching for:", searchQuery.value);
};

const showImageDetails = (image) => {
  selectedImage.value = image;
  dialogVisible.value = true;
};

const handleDetailedButton = (link) => {
  router.push(link);
};
</script>
  
<style scoped>
.gallery-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.el-header {
  padding: 20px;
  background-color: #f0f2f5;
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
}

.el-main {
  padding: 20px;
  overflow-y: auto;
}

.search-input {
  max-width: 500px;
}

.image-card {
  margin-bottom: 20px;
  transition: transform 0.3s;
  cursor: pointer;
}

.image-card:hover {
  transform: scale(1.05);
}

.image {
  width: 100%;
  display: block;
}

.image-info {
  padding: 10px;
}

.dialog-image {
  width: 100%;
  max-height: 70vh;
  object-fit: contain;
}

.image-dialog :deep(.el-dialog__body) {
  display: flex;
  flex-direction: column;
  align-items: center;
}

@media (max-width: 768px) {
  :deep(.el-dialog) {
    height: 500px;
  }
}
</style>