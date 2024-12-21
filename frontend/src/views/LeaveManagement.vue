<template>
  <a-layout class="leave-management">
    <a-layout-content>
      <div class="leave-container">
        <h1>请假管理</h1>
        <!-- 在这里你可以展示请假记录、审批等功能 -->
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'LeaveManagement'
}
</script>

<style scoped>
.leave-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
}

h1 {
  color: #001529;
}
</style>
