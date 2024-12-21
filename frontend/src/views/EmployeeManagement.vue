<template>
  <a-layout class="employee-management">
    <a-layout-content>
      <div class="employee-container">
        <h1>员工管理</h1>
        <!-- 你可以在这里添加员工列表、搜索功能等 -->
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'EmployeeManagement'
}
</script>

<style scoped>
.employee-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
}

h1 {
  color: #001529;
}
</style>
