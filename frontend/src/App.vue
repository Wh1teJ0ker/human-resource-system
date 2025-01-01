<template>
  <a-layout style="min-height: 100vh">
    <!-- 只有当不是认证页面（登录/注册页面）时才显示顶部导航栏和侧边栏 -->
    <app-header v-if="!isAuthPage"></app-header>

    <a-layout>
      <app-sidebar v-if="!isAuthPage"></app-sidebar>

      <a-layout-content style="padding: 24px;">
        <router-view></router-view> <!-- 这里会展示路由匹配到的组件 -->
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import AppSidebar from './components/AppSidebar.vue';

export default {
  name: 'App',
  components: {
    AppHeader,
    AppSidebar,
  },
  computed: {
    isAuthPage() {
      // 判断当前路由是否为登录页面或注册页面
      return this.$route.name === 'login' || this.$route.name === 'register' || this.$route.name === 'user' ;
    },
  },
};
</script>

<style>
/* 可以在这里加全局样式 */
</style>
