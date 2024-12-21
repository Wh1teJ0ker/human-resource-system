import * as Icons from '@ant-design/icons-vue';
import { createApp } from 'vue'; // Vue 3 中的创建应用方法
import App from './App.vue';
import Antd from 'ant-design-vue';
//import 'ant-design-vue/dist/antd.css';
import "ant-design-vue/dist/reset.css";
import router from './router';  // 导入路由

const app = createApp(App);
app.use(Antd);

// 注册 Ant Design 图标
Object.keys(Icons).forEach(key => {
  app.component(key, Icons[key]);
});

app.mount('#app');
