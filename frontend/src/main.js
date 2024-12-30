import { createApp } from 'vue'; // Vue 3 的创建应用方法
import App from './App.vue';
import Antd from 'ant-design-vue';
import "ant-design-vue/dist/reset.css"; // 引入 Ant Design Vue 样式
import router from './router'; // 导入路由
import * as Icons from '@ant-design/icons-vue'; // 导入 Ant Design 图标组件
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn'; // 引入中文 locale
import { ConfigProvider } from 'ant-design-vue';
dayjs.locale('zh-cn'); // 设置默认语言为中文
// 创建 Vue 3 应用实例
const app = createApp(App);

// 使用 Ant Design Vue 插件并设置语言为中文
app.use(Antd);

// 注册所有 Ant Design 图标组件
Object.keys(Icons).forEach(key => {
  app.component(key, Icons[key]);
});

app.use(ConfigProvider, {
  locale: 'zh_CN', // 设置 Ant Design 的 locale 为中文
});

// 使用路由
app.use(router);

// 挂载 Vue 应用
app.mount('#app');
