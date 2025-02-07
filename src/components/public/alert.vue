<template>
  <el-alert
    v-if="isShowAlert"
    :title="alertTitle"
    :type="alertType"
    :description="alertDescription"
    show-icon
    class="tip-alert"
    @close="isShowAlert = false"
  />
</template>

<script setup>
import { ref } from "vue";

const isShowAlert = ref(false);
const alertTitle = ref("成功！");
const alertType = ref("success");
const alertDescription = ref("");

// 显示提示框
async function showAlert(params) {
  alertTitle.value = params.title;
  alertType.value = params.type;
  alertDescription.value = params.description;
  isShowAlert.value = false;
  isShowAlert.value = true;
}

defineExpose({
  showAlert,
});
</script>

<style>
.tip-alert {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 300px;
}
</style>