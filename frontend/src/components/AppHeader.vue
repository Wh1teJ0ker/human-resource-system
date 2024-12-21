<template>
  <a-layout-header class="header">
    <div class="logo" />
    <div class="header-content">
      <h2>人力资源管理系统</h2>
    </div>
  </a-layout-header>
</template>

<script>
export default {
  name: 'AppHeader',
};
</script>

<style scoped>
.header {
  background: #fff;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.header .logo {
  font-size: 20px;
  font-weight: bold;
}

.header-content h2 {
  font-size: 18px;
  margin: 0;
}
</style>
