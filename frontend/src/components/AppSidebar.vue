<template>
  <a-layout-sider width="200" class="sidebar">
    <a-menu
      theme="dark"
      mode="inline"
      :default-selected-keys="['1']"
    >
      <a-menu-item key="1">
        <a-icon type="dashboard" />
        <span>控制面板</span>
      </a-menu-item>
      <a-menu-item key="2">
        <a-icon type="team" />
        <span>员工管理</span>
      </a-menu-item>
      <a-menu-item key="3">
        <a-icon type="building" />
        <span>部门管理</span>
      </a-menu-item>
      <a-menu-item key="4">
        <a-icon type="check-circle" />
        <span>考勤管理</span>
      </a-menu-item>
      <a-menu-item key="5">
        <a-icon type="schedule" />
        <span>请假管理</span>
      </a-menu-item>
      <a-menu-item key="6">
        <a-icon type="money-collect" />
        <span>薪资管理</span>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script>
export default {
  name: 'AppSidebar',
};
</script>

<style scoped>
.sidebar {
  background: #001529;
  height: 100vh;
}

/* 设置菜单的图标大小和位置 */
a-menu-item {
  display: flex;
  align-items: center;
}

a-icon {
  margin-right: 10px; /* 图标和文字之间的间距 */
}
</style>
