<template>
  <div class="app-container">
    <!-- 导航栏 -->
    <el-header class="header">
      <div class="nav-container">
        <div class="logo">
          <img
            src="/images/MyFlowingFireflyWife/icon.png"
            alt="Logo"
            class="logo-img"
          />
          <span class="logo-text">MyFlowingFireflyWife</span>
        </div>
        <el-menu mode="horizontal" :ellipsis="false" class="nav-menu">
          <el-menu-item index="1">主页</el-menu-item>
          <el-menu-item index="2">下载</el-menu-item>
          <el-menu-item index="3">文档</el-menu-item>
          <el-menu-item index="4">
            <a
              href="https://github.com/PYmili/MyFlowingFireflyWife"
              target="_blank"
              rel="noopener"
            >
              GitHub
            </a>
          </el-menu-item>
        </el-menu>
      </div>
    </el-header>

    <!-- Hero部分 -->
    <div class="hero-section">
      <div class="hero-content">
        <h1>让你的桌面焕发生机</h1>
        <p class="hero-description">
          开源的动态壁纸软件，为你的桌面带来无限可能
        </p>
        <div class="hero-buttons">
          <el-button type="primary" size="large" @click="scrollToDownload">
            立即下载
          </el-button>
          <el-button size="large"> 查看文档 </el-button>
        </div>
      </div>
      <div class="hero-image">
        <img src="/images/MyFlowingFireflyWife/hero-image.png" alt="软件预览" />
      </div>
    </div>

    <!-- 下载部分 -->
    <div id="download" class="download-section">
      <h2>下载</h2>
      <el-row :gutter="20" class="download-cards">
        <el-col :xs="24" :sm="8" v-for="system in systems" :key="system.name">
          <el-card class="download-card">
            <template #header>
              <div class="card-header">
                <el-icon class="system-icon">
                  <component :is="system.icon" />
                </el-icon>
                <span>{{ system.name }}</span>
              </div>
            </template>
            <div class="download-info">
              <p>版本 {{ system.version }}</p>
              <el-button type="primary" @click="downloadApp(system)">
                下载
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 版本更新日志 -->
    <div class="changelog-section">
      <h2>更新日志</h2>
      <el-timeline>
        <el-timeline-item
          v-for="(version, index) in versions"
          :key="index"
          :timestamp="version.date"
          placement="top"
        >
          <el-card>
            <h3>{{ version.version }}</h3>
            <ul>
              <li v-for="(change, idx) in version.changes" :key="idx">
                {{ change }}
              </li>
            </ul>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>

    <!-- 功能特性 -->
    <div class="features-section">
      <h2>主要特性</h2>
      <el-row :gutter="20" class="feature-cards">
        <el-col
          :xs="24"
          :sm="8"
          v-for="feature in features"
          :key="feature.title"
        >
          <el-card class="feature-card">
            <el-icon class="feature-icon">
              <component :is="feature.icon" />
            </el-icon>
            <h3>{{ feature.title }}</h3>
            <p>{{ feature.description }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 页脚 -->
    <el-footer class="footer">
      <div class="footer-content">
        <p>&copy; 2024 MyFlowingFireflyWife. 基于 GPL-3.0 license 许可证开源。</p>
      </div>
    </el-footer>
  </div>
</template>
  
<script setup>
import { ref, shallowRef } from "vue";
import {
  Monitor,
  Cpu,
  Picture, Files
} from "@element-plus/icons-vue";

import WindowsIcon from "@/components/public/icon/WindowsIcon.vue";
import LinuxIcon from "@/components/public/icon/LinuxIcon.vue";
import MacOSIcon from "@/components/public/icon/MacOSIcon.vue";

const systems = shallowRef([
  {
    name: "Windows",
    version: "bate-v0.5",
    icon: WindowsIcon,
    url: "https://github.com/PYmili/MyFlowingFireflyWife/releases/download/beta-v0.5/MyFlowingFireflyWife-nuitka-beta-v0.5.zip",
  },
  {
    name: "macOS",
    version: "暂未开发",
    icon: MacOSIcon,
    url: "#",
  },
  {
    name: "Linux",
    version: "暂未开发",
    icon: LinuxIcon,
    url: "#",
  },
]);

const versions = shallowRef([
  {
    version: "beta-v0.5: Update beta-v-5 version with Live2D display functionality.",
    date: "2024-12-22",
    changes: [
      "Live2D 显示功能：新增了 Live2D 模型加载功能，用户现在可以与 Live2D 模型进行简单的互动。"
    ]
  },
  {
    version: "beta version 0.4 | 测试版 0.4",
    date: "2024-08-08",
    changes: [
      "管理界面UI功能和外观经过全面优化，提升了用户界面的美观度和易用性",
      "人物界面代码经过重构，显著提升了界面性能和响应速度",
      "基础设置选项中的功能实现已编写完成，支持即时修改并应用设置",
      "为尚未实现的功能按钮添加了弹窗提示，增强了用户的交互体验",
      "新增了通过基础设置调整人物大小缩放的功能，使用户能够根据需要自定义界面",
      "新增了通过基础设置控制程序启动和结束时的语音播放功能，提升了用户体验的个性化"
    ]
  },
  {
    version: "beta version 0.3 | 测试版 0.3",
    date: "2024-07-30",
    changes: [
      "添加了必要的静态文件以支持项目功能", 
      "调整了项目结构，提高了代码的组织性和可维护性", 
      "引入了pyinstaller进行项目打包，以便于分发和部署",
      "取消了nuitka的使用，优化了构建过程",
      "修复了程序退出时出现的卡死情况，增强了程序的稳定性",
      "解决了语音播放失败的问题，提升了用户体验",
      "对程序的启动流程进行了优化，缩短了启动时间",
      "减少了启动时的资源消耗，提高了程序的响应速度"
    ],
  },
  {
    version: "[Beta] beta-v0.2 windows | WIndows平台的测试版v0.2",
    date: "2024-06-30",
    changes: [
      "新增/优化",
      "插件支持：我们引入了新的插件系统，允许用户根据需要扩展和自定义应用功能。",
      "电池语音包功能：新增的插件自带电池语音包功能，用户将收到设备电池状态的语音通知。",
      "打包策略优化：我们优化了应用的打包策略，确保更高效的应用分发。",
      "启动问题优化：解决了之前的启动问题，改善了启动流程，使用户能够更顺畅地开始使用应用。"
    ],
  },
  {
    version: "[Beta] beta-v0.1 windows | WIndows平台的测试版v0.1",
    date: "2024-06-17",
    changes: [
      "首次发布",
      "开源免费：完全免费，源代码开放，欢迎您的参与和反馈。",
      "个性化定制：测试版允许您定制流萤的外观和行为。",
      "新功能测试：尝试我们的语音包功能和新增的游走动作。",
      "GUI优化：测试版改进了摸头判定和窗口大小调整，优化用户体验。"
    ],
  },
]);

const features = shallowRef([
  {
    title: "开源免费",
    description: "开源免费，不收取任何使用费用",
    icon: Files,
  },
  {
    title: "语音交互",
    description: "宠物语音，与使用者交互",
    icon: Monitor,
  },
  {
    title: "动作样式",
    description: "多种动作与样式，支持live2d",
    icon: Picture,
  },
]);

const scrollToDownload = () => {
  document.getElementById("download").scrollIntoView({ behavior: "smooth" });
};

const downloadApp = (system) => {
  // 实现下载逻辑
  console.log(`Downloading for ${system.name}`);
  const link = document.createElement("a");
  link.style.display = "none";
  link.href = system.url;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};
</script>
  
<style scoped>
.app-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.header {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: fixed;
  width: 100%;
  z-index: 100;
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 60px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo-img {
  height: 40px;
  width: 40px;
}

.logo-text {
  font-size: 1.5rem;
  font-weight: bold;
  color: #409eff;
}

.nav-menu {
  border-bottom: none;
}

.hero-section {
  padding: 120px 20px 60px;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  gap: 40px;
}

.hero-content {
  flex: 1;
}

.hero-content h1 {
  font-size: 3rem;
  margin-bottom: 20px;
  color: #303133;
}

.hero-description {
  font-size: 1.5rem;
  color: #606266;
  margin-bottom: 30px;
}

.hero-buttons {
  display: flex;
  gap: 20px;
}

.hero-image {
  flex: 1;
}

.hero-image img {
  width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.download-section,
.changelog-section,
.features-section {
  padding: 60px 20px;
  max-width: 1200px;
  margin: 0 auto;
}

h2 {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 40px;
  color: #303133;
}

.download-cards,
.feature-cards {
  margin-bottom: 40px;
}

.download-card,
.feature-card {
  height: 100%;
  text-align: center;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
}

.system-icon {
  font-size: 24px;
}

.download-info {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
}

.feature-card {
  padding: 20px;
}

.feature-icon {
  font-size: 48px;
  margin-bottom: 20px;
  color: #409eff;
}

.footer {
  background-color: #303133;
  color: #fff;
  padding: 40px 20px;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.social-links {
  margin-top: 20px;
  background-color: #a1a1a1;
}

.social-links a {
  color: black;
  font-size: 24px;
  margin: 0 10px;
}

.social-links .el-icon {
  font-size: 24px;
}

@media (max-width: 768px) {
  .hero-section {
    flex-direction: column;
    text-align: center;
    padding-top: 100px;
  }

  .hero-buttons {
    justify-content: center;
  }

  .hero-content h1 {
    font-size: 2rem;
  }

  .hero-description {
    font-size: 1.2rem;
  }
}
</style>