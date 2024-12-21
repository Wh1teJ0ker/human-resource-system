<template>
  <a-layout-sider width="200" class="sidebar">
    <a-menu
      theme="light"
      mode="inline"
      :default-selected-keys="['1']"
      @select="handleMenuSelect"
    >
      <a-menu-item key="1">
        <router-link to="/">
          <a-icon type="dashboard" />
          <span>控制面板</span>
        </router-link>
      </a-menu-item>
      <a-menu-item key="2">
        <router-link to="/employee-management">
          <a-icon type="team" />
          <span>员工管理</span>
        </router-link>
      </a-menu-item>
      <a-menu-item key="3">
        <router-link to="/department-management">
          <a-icon type="building" />
          <span>部门管理</span>
        </router-link>
      </a-menu-item>
      <a-menu-item key="4">
        <router-link to="/attendance-management">
          <a-icon type="check-circle" />
          <span>考勤管理</span>
        </router-link>
      </a-menu-item>
      <a-menu-item key="5">
        <router-link to="/leave-management">
          <a-icon type="schedule" />
          <span>请假管理</span>
        </router-link>
      </a-menu-item>
      <a-menu-item key="6">
        <router-link to="/salary-management">
          <a-icon type="money-collect" />
          <span>薪资管理</span>
        </router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script>
export default {
  name: 'AppSidebar',
  methods: {
    handleMenuSelect(info) {
      console.log(info);
    }
  }
};
</script>

<style scoped>
.sidebar {
  background:rgb(248, 251, 254);
  height: 100vh;
}
</style>
