<template>
  <a-layout class="department-management">
    <a-layout-content>
      <div class="department-container">
        <h1>部门管理</h1>
        <!-- 你可以在这里添加部门列表、创建部门等功能 -->
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'DepartmentManagement'
}
</script>

<style scoped>
.department-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
}

h1 {
  color: #001529;
}
</style>
