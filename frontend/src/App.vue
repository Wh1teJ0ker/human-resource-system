<template>
  <a-layout>
    <!-- 侧边栏 -->
    <AppSidebar />

    <a-layout>
      <!-- 顶部导航栏 -->
      <AppHeader />
      <a-layout-content style="padding: 24px">
        <router-view /> <!-- 页面内容 -->
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
// 导入公共组件
import AppSidebar from './components/AppSidebar.vue';
import AppHeader from './components/AppHeader.vue';

export default {
  name: 'App',
  components: {
    AppSidebar,
    AppHeader
  }
};
</script>

<style>
@import '~ant-design-vue/dist/reset.css';
</style>
