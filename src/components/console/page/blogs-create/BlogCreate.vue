<template>
  <el-container class="blog-container">
    <el-aside width="200px" class="blog-sidebar">
      <el-menu :default-active="activeMenu" @select="handleMenuSelect">
        <el-menu-item index="list">
          <el-icon><Document /></el-icon>
          <span>文章列表</span>
        </el-menu-item>
        <el-menu-item index="create">
          <el-icon><Edit /></el-icon>
          <span>新建文章</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container class="main-container">
      <el-header>
        <h2>{{ pageTitle }}</h2>
      </el-header>

      <el-main>
        <!-- 文章列表视图 -->
        <div v-if="activeMenu === 'list'" class="article-list">
          <el-row :gutter="20">
            <el-col :span="8" v-for="article in articles" :key="article.id">
              <el-card class="article-card" shadow="hover">
                <div class="article-card-header">
                  <h3 class="article-title">{{ article.title }}</h3>
                  <span class="article-date">{{
                    formatDate(article.publishDate)
                  }}</span>
                </div>
                <div class="article-preview">
                  {{ article.content.substring(0, 100) }}...
                </div>
                <div class="article-footer">
                  <span class="article-author">作者: {{ article.author }}</span>
                  <el-button
                    type="primary"
                    link
                    @click="editArticle(article.id)"
                  >
                    编辑
                  </el-button>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>

        <!-- 文章编辑视图 -->
        <div v-else class="article-editor">
          <el-form :model="blogForm" label-width="80px">
            <el-form-item label="标题">
              <el-input
                v-model="blogForm.title"
                placeholder="请输入文章标题"
              ></el-input>
            </el-form-item>

            <el-form-item label="内容" class="content-item">
              <div class="editor-container">
                <div class="editor-toolbar">
                  <el-button-group>
                    <el-button @click="insertMd('bold')" title="粗体"
                      >B</el-button
                    >
                    <el-button @click="insertMd('italic')" title="斜体"
                      >I</el-button
                    >
                    <el-button @click="insertMd('header')" title="标题"
                      >#</el-button
                    >
                    <el-button @click="insertMd('link')" title="链接">
                      <el-icon><Link /></el-icon>
                    </el-button>
                    <el-button @click="insertMd('image')" title="图片">
                      <el-icon><Picture /></el-icon>
                    </el-button>
                    <el-button @click="insertMd('code')" title="代码块">
                      <el-icon><Monitor /></el-icon>
                    </el-button>
                  </el-button-group>
                </div>

                <div class="editor-content">
                  <el-input
                    v-model="blogForm.content"
                    type="textarea"
                    :rows="20"
                    placeholder="请输入文章内容（支持Markdown格式）"
                  ></el-input>
                  <div class="markdown-preview" v-html="markdownPreview"></div>
                </div>
              </div>
            </el-form-item>

            <el-form-item label="作者">
              <el-input
                v-model="blogForm.author"
                placeholder="请输入作者姓名"
              ></el-input>
            </el-form-item>

            <el-form-item label="发布日期">
              <el-date-picker
                v-model="blogForm.publishDate"
                type="date"
                placeholder="选择发布日期"
              ></el-date-picker>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="saveBlog">{{
                isEditing ? "更新" : "发布"
              }}</el-button>
              <el-button @click="resetForm">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { ElMessage } from "element-plus";
import {
  Document,
  Edit,
  Link,
  Picture,
  Monitor,
} from "@element-plus/icons-vue";
import MarkdownIt from "markdown-it";

const md = new MarkdownIt();
const activeMenu = ref("list");
const isEditing = ref(false);
const articles = ref([]);
const blogForm = ref({
  title: "",
  content: "",
  author: "",
  publishDate: "",
});

const pageTitle = computed(() => {
  if (activeMenu.value === "list") return "文章列表";
  return isEditing.value ? "编辑文章" : "创建新文章";
});

const markdownPreview = computed(() => {
  return md.render(blogForm.value.content || "");
});

// 格式化日期
const formatDate = (date) => {
  if (!date) return "";
  return new Date(date).toLocaleDateString("zh-CN");
};

// 模拟获取文章列表
const fetchArticles = async () => {
  // 实际项目中这里应该调用后端API
  articles.value = [
    {
      id: 1,
      title: "示例文章1",
      content: "这是第一篇示例文章的内容，包含了一些示例文字...",
      author: "张三",
      publishDate: "2024-01-01",
    },
    {
      id: 2,
      title: "示例文章2",
      content: "这是第二篇示例文章的内容，展示了更多的示例场景...",
      author: "李四",
      publishDate: "2024-01-02",
    },
    {
      id: 3,
      title: "示例文章3",
      content: "这是第三篇示例文章，用于测试文章列表的显示效果...",
      author: "王五",
      publishDate: "2024-01-03",
    },
  ];
};

// 处理菜单选择
const handleMenuSelect = (index) => {
  activeMenu.value = index;
  if (index === "list") {
    fetchArticles();
  }
};

// 编辑文章
const editArticle = (id) => {
  const article = articles.value.find((a) => a.id === id);
  if (article) {
    blogForm.value = { ...article };
    isEditing.value = true;
    activeMenu.value = "create";
  }
};

const insertMd = (type) => {
  const insertText = {
    bold: "**粗体文本**",
    italic: "*斜体文本*",
    header: "# 标题",
    link: "[链接文本](url)",
    image: "![图片描述](图片地址)",
    code: "```\n代码块\n```",
  };

  blogForm.value.content += insertText[type];
};

const saveBlog = async () => {
  try {
    // 实际项目中这里应该调用后端API
    await new Promise((resolve) => setTimeout(resolve, 1000));
    ElMessage.success(isEditing.value ? "文章更新成功！" : "文章发布成功！");
    activeMenu.value = "list";
    fetchArticles();
  } catch (error) {
    ElMessage.error("操作失败，请重试");
  }
};

const resetForm = () => {
  blogForm.value = {
    title: "",
    content: "",
    author: "",
    publishDate: "",
  };
  isEditing.value = false;
};

onMounted(() => {
  fetchArticles();
});
</script>
  
  <style scoped>
.blog-container {
  height: 94vh;
  width: 100%;
}

.blog-sidebar {
  background-color: #f5f7fa;
  border-right: 1px solid #e6e6e6;
}

.main-container {
  flex: 1;
}

.el-header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 20px;
  line-height: 60px;
}

.article-list {
  padding: 20px;
}

.article-card {
  margin-bottom: 20px;
  height: 100%;
}

.article-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.article-title {
  margin: 0;
  font-size: 18px;
  color: #303133;
}

.article-date {
  color: #909399;
  font-size: 14px;
}

.article-preview {
  color: #606266;
  margin-bottom: 15px;
  height: 60px;
  overflow: hidden;
  text-overflow: ellipsis;
}

.article-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #909399;
}

.content-item :deep(.el-form-item__content) {
  margin-left: 0 !important;
}

.editor-container {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  width: 100%;
}

.editor-toolbar {
  padding: 8px;
  border-bottom: 1px solid #dcdfe6;
  background-color: #f5f7fa;
}

.editor-content {
  display: flex;
  min-height: 600px;
}

.el-input.el-textarea {
  width: 50%;
  border-right: 1px solid #dcdfe6;
}

.markdown-preview {
  width: 50%;
  padding: 15px;
  overflow-y: auto;
  background-color: #fff;
}

.el-textarea :deep(.el-textarea__inner) {
  height: 100%;
  resize: none;
  border: none;
  border-radius: 0;
}

.article-editor {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}
</style>