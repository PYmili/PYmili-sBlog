<template>
    <div class="my-life">
        <el-steps
            :active="3"
            finish-status="success"
            class="el-steps-class"
        >
            <div class="left-title">
                <h1>我的人生</h1>
            </div>
            <div class="step">
                <el-step title="出生"></el-step>
                <el-step title="活到18"></el-step>
                <el-step title="进入大学"></el-step>
                <el-step title="找到工作"></el-step>
                <el-step title="月入百万"></el-step>
                <el-step title="死"></el-step>
            </div>
        </el-steps>
    </div>
    
</template>
<script setup>

</script>

<style scoped>
    .my-life {
        width: 100%;
        height: 400px;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #00000026;
    }
    .el-steps-class {
        width: 90%;
        display: flex;
        justify-content: flex-start;
    }
    .el-steps-class .left-title h1 {
        font-size: 40px;
        /* color: white; */
    }
    .el-steps-class .step {
        margin-left: 50px;
        width: 100%;
        display: flex;
        justify-content: flex-end;
    }
</style>