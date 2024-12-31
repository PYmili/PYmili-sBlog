<template>
  <div class="blog-detail-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <h1>{{ blog.title }}</h1>
          <el-tag size="small">{{ blog.category }}</el-tag>
        </div>
      </template>
      <div class="blog-content">
        <div class="info">
          <el-avatar :size="32" :src="blog.authorAvatar"></el-avatar>
          <span class="author">{{ blog.author }}</span>
          <el-divider direction="vertical"></el-divider>
          <span class="date">
            <el-icon><Calendar /></el-icon>
            {{ formatDate(blog.publishDate) }}
          </span>
        </div>
        <div class="content markdown-body" v-html="renderedContent"></div>
      </div>
      <div class="tags">
        <el-icon><Collection /></el-icon>
        <el-tag v-for="tag in blog.tags" :key="tag" class="mx-1" effect="plain">
          {{ tag }}
        </el-tag>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { Calendar, Collection } from "@element-plus/icons-vue";
import MarkdownIt from "markdown-it";

const md = new MarkdownIt();

// 定义 props，并使用解构赋值来获取 blog 对象
const { blog } = defineProps({
  blog: {
    type: Object,
    default: () => ({}),
  },
});

const renderedContent = computed(() => {
  return md.render(blog.content);
});

const formatDate = (date) => {
  return new Date(date).toLocaleDateString("zh-CN", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};
</script>

<style>
.blog-detail-container {
  display: flex;
  justify-content: center;
  align-items: stretch;
  min-height: 100vh;
  padding: 2rem;
  background-color: #f0f2f5;
}

.box-card {
  width: 100%;
  max-width: 800px;
  display: flex;
  flex-direction: column;
}

.blog-content {
  position: relative;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h1 {
  margin: 0;
  font-size: 1.8rem;
}

.info {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
  color: #606266;
}

.author {
  margin-left: 0.5rem;
}

.date {
  display: flex;
  align-items: center;
}

.date .el-icon {
  margin-right: 0.25rem;
}

.content {
  flex-grow: 1;
  overflow-y: auto;
  padding: 1rem;
  background-color: #ffffff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.tags {
  position: absolute;
  bottom: 0;
  display: flex;
  align-items: center;
  margin-top: 1rem;
}

.tags .el-icon {
  margin-right: 0.5rem;
}

.el-tag {
  margin-right: 0.5rem;
}

/* Markdown 样式 */
.markdown-body {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial,
    sans-serif;
  font-size: 16px;
  line-height: 1.5;
  word-wrap: break-word;
  overflow-wrap: break-word;
  max-width: 100%;
}

.markdown-body * {
  max-width: 100%;
}

.markdown-body ul,
.markdown-body ol {
  padding-left: 2em;
  margin-left: 0;
  max-width: calc(100% - 2em);
}

.markdown-body pre,
.markdown-body code {
  white-space: pre-wrap;
  word-wrap: break-word;
}

.markdown-body img {
  max-width: 100%;
  height: auto;
}

.markdown-body table {
  display: block;
  width: 100%;
  overflow-x: auto;
}

.markdown-body h1,
.markdown-body h2 {
  border-bottom: 1px solid #eaecef;
  padding-bottom: 0.3em;
}

.markdown-body code {
  background-color: rgba(27, 31, 35, 0.05);
  border-radius: 3px;
  font-size: 85%;
  margin: 0;
  padding: 0.2em 0.4em;
}

.markdown-body pre {
  background-color: #f6f8fa;
  border-radius: 3px;
  font-size: 85%;
  line-height: 1.45;
  overflow: auto;
  padding: 16px;
}

.markdown-body blockquote {
  border-left: 0.25em solid #dfe2e5;
  color: #6a737d;
  padding: 0 1em;
}

.markdown-body table {
  border-collapse: collapse;
  display: block;
  overflow: auto;
  width: 100%;
}

.markdown-body table th,
.markdown-body table td {
  border: 1px solid #dfe2e5;
  padding: 6px 13px;
}

.markdown-body table tr {
  background-color: #fff;
  border-top: 1px solid #c6cbd1;
}

.markdown-body table tr:nth-child(2n) {
  background-color: #f6f8fa;
}
</style>

// 假设这是我们从API获取的博客数据
// const blog = ref({
//   title: 'Vue 3 和 Element Plus 的完美结合',
//   author: 'John Doe',
//   authorAvatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
//   publishDate: new Date('2023-05-20'),
//   content: `
// # 欢迎使用 Markdown

// 这是一个示例 Markdown 内容。

// ## 特性

// - 支持 **粗体** 和 *斜体*
// - 支持 \`代码块\`
// - 支持列表和表格

// ## 代码示例

// \`\`\`javascript
// console.log('Hello, Markdown!');
// \`\`\`

// ## 表格示例

// | 列1 | 列2 | 列3 |
// |-----|-----|-----|
// | 内容1 | 内容2 | 内容3 |

// 这只是一个简单的示例，实际的博客内容可能会更长。
//   `,
//   category: '前端开发',
//   tags: ['Vue', 'Element Plus', 'Markdown'],
// })