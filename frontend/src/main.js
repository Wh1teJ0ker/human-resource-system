import * as Icons from '@ant-design/icons-vue';
import { createApp } from 'vue'; // Vue 3 的创建应用方法
import App from './App.vue';
import Antd from 'ant-design-vue';
//import 'ant-design-vue/dist/antd.css';  // 你已经注释掉了这一行，使用了reset.css
import "ant-design-vue/dist/reset.css";
import router from './router';  // 导入路由
import { Button } from 'ant-design-vue';
// 创建 Vue 3 应用实例
const app = createApp(App);

// 使用 Ant Design Vue 插件
app.use(Antd);

// 注册 Ant Design 图标组件
Object.keys(Icons).forEach(key => {
  app.component(key, Icons[key]);
});

export default {
  components: {
    'a-button': Button,
  },
};

// 使用路由
app.use(router);

// 挂载 Vue 应用
app.mount('#app');
