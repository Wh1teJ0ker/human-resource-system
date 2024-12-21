<template>
  <a-layout-header class="header">
    <!-- Logo 和标题区域 -->
    <div class="logo">
      <a-icon type="user" style="font-size: 24px; color: #1890ff;" />
      <span class="logo-text">人力资源管理系统</span>
    </div>
    <!-- 右侧内容 -->
    <div class="header-content">
      <a-dropdown>
        <a-button>
          <span>{{ user.username || '管理员' }}</span>
        </a-button>
        <template v-slot:overlay>
          <a-menu>
            <a-menu-item key="1" @click="goToAccountSettings">修改管理员账户</a-menu-item>
            <a-menu-item key="2" @click="logout">登出</a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>
  </a-layout-header>
</template>

<script>
export default {
  name: 'AppHeader',
  data() {
    return {
      user: {
        username: localStorage.getItem('username') || '管理员', // 默认用户名
      },
    };
  },
  methods: {
    // 跳转到修改管理员账户页面
    goToAccountSettings() {
      this.$router.push({ name: 'account-settings' });
    },
    // 处理登出
    logout() {
      // 清除认证相关信息
      localStorage.removeItem('authToken');
      localStorage.removeItem('username');
      this.user.username = ''; // 清空用户名显示

      // 跳转到登录页面
      this.$router.push({ name: 'login' });
    },
  },
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
  display: flex;
  align-items: center;
}

.header .logo span.logo-text {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin-left: 10px;  /* Logo 和标题之间的间距 */
}

.header-content {
  display: flex;
  align-items: center;
}

.header-content a-dropdown {
  display: flex;
  align-items: center;
}

.header-content .ant-btn {
  margin-left: 20px;  /* 登录按钮与右侧内容之间的间距 */
}

.header .logo a-icon {
  font-size: 24px;  /* 调整图标大小 */
  color: #1890ff;  /* 图标颜色 */
}
</style>
