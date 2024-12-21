<template>
  <a-layout class="attendance-management">
    <a-layout-content>
      <div class="attendance-container">
        <h1>考勤管理</h1>
        <!-- 在这里你可以展示考勤记录、考勤统计等 -->
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'AttendanceManagement'
}
</script>

<style scoped>
.attendance-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
}

h1 {
  color: #001529;
}
</style>
