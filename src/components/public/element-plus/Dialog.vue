<template>
  <el-dialog
    v-model="dialogVisible"
    :title="title"
    :width="width"
    :before-close="handleClose"
  >
    <span>{{ message }}</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button 
          @click="handleCancelAndConfirm(`cancel`)"
        >
          取消
        </el-button>
        <el-button 
          type="primary" 
          @click="handleCancelAndConfirm(`confirm`)"
        >
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { ElMessageBox } from "element-plus";

const dialogVisible = ref(false);
const title = ref("dialog");
const width = ref(500);
const message = ref('None');

let handleClose = (done: () => void) => {
  ElMessageBox.confirm('您确定要关闭此对话框吗？')
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};
let handleCancel = () => {};
let handleConfirm = () => {};

function handleCancelAndConfirm(_handle) {
  dialogVisible.value = false;
  if (_handle === 'cancel') {
    return handleCancel();
  } else {
    return handleConfirm();
  }
}

function init(params: { title?: string; width?: number; message?: string; handleClose?: (done: () => void) => void; handleCancel?: () => void; handleConfirm?: () => void }) {
  title.value = params.title || title.value;
  width.value = params.width || width.value;
  message.value = params.message || message.value;
  if (params.handleClose) {
    handleClose = params.handleClose;
  }
  if (params.handleCancel) {
    handleCancel = params.handleCancel;
  }
  if (params.handleConfirm) {
    handleConfirm = params.handleConfirm;
  }
}

function isShow(_is: boolean) {
  dialogVisible.value = _is;
}

defineExpose({
  init,
  isShow
});
</script>

<style>

</style>