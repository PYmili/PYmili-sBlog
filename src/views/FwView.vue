<template>
  <div class="product-showcase">
    <!-- Hero Section -->
    <section class="hero">
      <el-row class="hero-content" justify="center" align="middle">
        <el-col :span="24" :md="5">
          <h1>FW-Bot</h1>
          <p>一种 “抠门，懒惰，傻” 的机器人</p>
          <el-button type="primary" size="large" @click="scrollToElement">
            立即查看
          </el-button>
        </el-col>
        <el-col :span="24" :md="12" class="hero-image">
          <el-image src="/images/gallery/fw-fuzhu.png" fit="contain" />
        </el-col>
      </el-row>
    </section>

    <!-- Features Section -->
    <section class="features" id="features">
      <h2>痴傻情况</h2>
      <el-row :gutter="20">
        <el-col
          :span="24"
          :md="8"
          v-for="feature in features"
          :key="feature.title"
        >
          <el-card class="feature-card" :body-style="{ padding: '0px' }">
            <img :src="feature.icon" class="feature-icon" />
            <div style="padding: 14px">
              <h3>{{ feature.title }}</h3>
              <p>{{ feature.description }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </section>

    <!-- How It Works Section -->
    <section class="how-it-works">
      <h2>工作原理</h2>
      <el-steps :active="4" finish-status="success" align-center>
        <el-step
          v-for="step in workSteps"
          :key="step.title"
          :title="step.title"
          :description="step.description"
        />
      </el-steps>
    </section>

    <!-- Testimonials Section -->
    <section class="testimonials">
      <h2>用户反馈</h2>
      <el-carousel :interval="4000" type="card" height="300px">
        <el-carousel-item
          v-for="testimonial in testimonials"
          :key="testimonial.name"
        >
          <el-card class="testimonial-card">
            <template #header>
              <div class="testimonial-header">
                <el-avatar :size="64" :src="testimonial.avatar" />
                <h3>{{ testimonial.name }}</h3>
              </div>
            </template>
            <p>"{{ testimonial.content }}"</p>
            <el-rate v-model="testimonial.rating" disabled show-score />
          </el-card>
        </el-carousel-item>
      </el-carousel>
    </section>

    <!-- Pricing Section -->
    <section class="pricing">
      <h2>选择您的计划</h2>
      <el-row :gutter="20" justify="center">
        <el-col
          :span="24"
          :md="8"
          v-for="plan in pricingPlans"
          :key="plan.name"
        >
          <el-card class="pricing-card" :body-style="{ padding: '0px' }">
            <div style="padding: 14px">
              <h3>{{ plan.name }}</h3>
              <div class="price">{{ plan.price }}</div>
              <ul style="list-style-type: none">
                <li v-for="feature in plan.features" :key="feature">
                  {{ feature }}
                </li>
              </ul>
              <el-button type="primary" plain>选择</el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </section>

    <!-- Footer -->
    <el-footer class="footer">
      <p>&copy; 2025 PYmili'sBlog. 保留所有权利。</p>
    </el-footer>
  </div>
</template>
  
<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";

const features = ref([
  {
    title: "啥也不会",
    description: "问啥都不会，不如洗洗睡。",
    icon: "/images/fw/fw-icon.png",
  },
  {
    title: "单核cpu",
    description: "和fw一样，单纯且痴傻。",
    icon: "/images/fw/fw-icon-1.png",
  },
  {
    title: "抠门到家",
    description: "没人比我还会抠门",
    icon: "/images/fw/fw-icon.png",
  },
]);

const workSteps = ref([
  { title: "思考", description: "如何成为一个合格的fw" },
  { title: "分析", description: "fw的主要组成原理" },
  { title: "洞察", description: "只要我够抠门，事就会少" },
  { title: "行动", description: "捣乱我是认真的" },
]);

const testimonials = ref([
  {
    name: "PYmili",
    content: "FW-Bot大大降低了我的工作效率！",
    rating: 5,
    avatar: "/images/fw/pymili.jpg",
  },
  {
    name: "PYmili",
    content: "FW-Bot是真正的抠门Bot！",
    rating: 4.5,
    avatar: "/images/fw/pymili.jpg",
  },
  {
    name: "PYmili",
    content: "FW-Bot不仅仅是痴傻！",
    rating: 4.8,
    avatar: "/images/fw/pymili.jpg",
  },
]);

const pricingPlans = ref([
  {
    name: "基础版",
    price: "¥99/月",
    features: ["痴傻对话", "无脑思考", "降低效率"],
  },
  {
    name: "专业版",
    price: "¥299/月",
    features: ["小脑对话", "胡乱思考", "加大损失", "没有售后服务"],
  },
  {
    name: "企业版",
    price: "联系我们",
    features: ["无脑对话", "不会思考", "没有效率", "殴打雇主模式"],
  },
]);

const scrollToElement = () => {
  const targetElement = document.getElementById("features");
  if (targetElement) {
    targetElement.scrollIntoView({ behavior: "smooth" });
  }
};
</script>
  
<style scoped>
.product-showcase {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

section {
  padding: 80px 20px;
  text-align: center;
}

h1 {
  font-size: 3rem;
  margin-bottom: 20px;
}

h2 {
  font-size: 2.5rem;
  margin-bottom: 40px;
}

.hero {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  min-height: 90vh;
  display: flex;
  align-items: center;
}

.hero-content {
  width: 100%;
}

.hero p {
  font-size: 1.5rem;
  margin-bottom: 30px;
}

.hero-image :deep(.el-image) {
  border-radius: 15px;
}

.features {
  background-color: #f8f9fa;
}

.feature-card {
  height: 100%;
  transition: all 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 4px 25px 0 rgba(0, 0, 0, 0.1);
}

.feature-icon {
  width: 150px;
  height: 150px;
  margin-top: 10px;
}

.how-it-works {
  background-color: white;
}

.testimonials {
  background-color: #f8f9fa;
}

.testimonial-card {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.testimonial-header {
  display: flex;
  align-items: center;
  gap: 20px;
}

.pricing {
  background-color: white;
}

.pricing-card {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.price {
  font-size: 2rem;
  font-weight: bold;
  margin: 20px 0;
}

.footer {
  background-color: #333;
  color: white;
  text-align: center;
  padding: 20px;
}

@media (max-width: 768px) {
  .hero {
    text-align: center;
  }

  .hero-image {
    margin-top: 40px;
  }
}
</style>