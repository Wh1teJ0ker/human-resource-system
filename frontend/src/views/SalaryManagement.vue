<template>
  <a-layout class="salary-management">
    <a-layout-content>
      <div class="salary-container">
        <h1>薪资管理</h1>
        <!-- 你可以在这里展示薪资列表、薪资统计等功能 -->
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'SalaryManagement'
}
</script>

<style scoped>
.salary-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
}

h1 {
  color: #001529;
}
</style>
