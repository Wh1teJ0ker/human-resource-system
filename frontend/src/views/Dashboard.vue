<template>
  <a-layout class="dashboard">
    <a-layout-content>
      <div class="dashboard-container">
        <h1>欢迎来到控制面板</h1>
        <!-- 在这里你可以添加更多的统计图表、卡片组件等 -->
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'Dashboard'
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
}

h1 {
  color: #001529;
}
</style>
