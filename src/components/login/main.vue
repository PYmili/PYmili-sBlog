<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-content">
        <div class="image-container">
          <img src="/images/login/119671932_p0.jpg" alt="Login Image" class="login-image">
        </div>
        <div class="form-container">
          <h2 class="login-title">欢迎登录</h2>
          <el-form
            label-position="top"
            v-model="formRef"
          >
            <el-form-item label="用户名">
              <el-input
                v-model="formRef.username"
                :prefix-icon="User"
                placeholder="请输入用户名"
              />
            </el-form-item>
            <el-form-item label="密码">
              <el-input 
                v-model="formRef.password"
                type="password" 
                :prefix-icon="Lock"
                show-password
                placeholder="请输入密码"
              />
            </el-form-item>
            <el-button 
                type="primary" 
                native-type="button" 
                class="login-button"
                @click="tiggerUserLogin"
              >
                登录
            </el-button>
          </el-form>
          <div class="additional-options">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary">忘记密码？</el-link>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const formRef = ref({
    username: '',
    password: ''
});
const emit = defineEmits(['tiggerUserLogin'])

function tiggerUserLogin() {
  emit('tiggerUserLogin', formRef)
}

</script>

<style scoped>
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    width: 100%;
    padding: 20px;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  }

  .login-card {
    width: 100%; /* 使登录卡片宽度自适应 */
    max-width: 950px; /* 设置一个最大宽度，可根据需要调整 */
    border-radius: 20px;
    overflow: hidden; /* 去除多余的阴影 */
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
    background-color: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(10px);
    display: flex;
    flex-direction: column;
  }

  .login-content {
    flex: 1;
    display: flex;
    flex-direction: row;
    min-height: 0; 
  }

  .image-container {
    flex: 1; /* 使图片容器与表单容器等宽 */
    overflow: hidden;
    position: relative;
  }

  .login-image {
    width: 100%;
    height: 100%; /* 使图片高度自适应 */
    object-fit: cover;
    object-position: center;
    border-radius: 15px 0 0 15px;
  }

  .form-container {
    flex: 2; /* 使表单容器与图片容器等宽 */
    padding: 30px;
    display: flex;
    flex-direction: column;
    overflow-y: auto; 
    max-height: 100%; 
  }

  .login-title {
    text-align: center;
    color: #409EFF;
    font-size: 24px;
    margin-bottom: 20px;
    font-weight: 600;
  }

  .login-button {
    width: 100%;
    height: 40px;
    font-size: 16px;
    font-weight: 600;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    transition: all 0.3s ease;
  }

  .login-button:hover {
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  }

  .additional-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 15px;
  }

  :deep(.el-input__wrapper) {
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    border-radius: 8px;
  }

  :deep(.el-input__inner) {
    height: 40px;
  }

  :deep(.el-form-item__label) {
    font-weight: 600;
    color: #606266;
  }

  :deep(.el-checkbox__label) {
    color: #606266;
  }

  :deep(.el-link) {
    font-weight: 600;
  }

  :deep(.el-card__body) {
    height: 100%;
    padding: 0;
  }

  :deep(.el-form) {
    flex: 1;
    display: flex;
    flex-direction: column;
  }

  :deep(.el-form-item) {
    margin-bottom: 15px;
  }

  :deep(.el-form-item:last-child) {
    margin-top: auto;
    margin-bottom: 0;
  }

  @media (max-width: 768px) {
    .login-card {
      max-width: 100%;
    }
    .login-content {
      flex-direction: column;
    }
    
    .image-container {
      flex: 0 0 0;
    }
    
    .form-container {
      flex: 1;
      padding: 20px;
    }
  }
</style>

