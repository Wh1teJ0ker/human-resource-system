<template>
  <a-layout class="login-layout">
    <a-layout-content>
      <div class="login-background">
        <div class="login-container">
          <h1>后台管理系统</h1>
          <a-form :form="loginForm" @submit="handleLogin" class="login-form">
            <a-form-item label="用户名" :label-col="{ span: 6 }" :wrapper-col="{ span: 14 }">
              <a-input v-model:value="loginForm.username" placeholder="请输入用户名" />
            </a-form-item>

            <a-form-item label="密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 14 }">
              <a-input v-model:value="loginForm.password" type="password" placeholder="请输入密码" />
            </a-form-item>

            <a-form-item :wrapper-col="{ span: 14, offset: 6 }">
              <a-button type="primary" html-type="submit" class="login-form-button">
                登录
              </a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'LoginPage',  // 多词组件名
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
      },
      testAccount: {
        username: 'admin',
        password: 'admin',
      },
    };
  },
  methods: {
    // 处理登录
    handleLogin(e) {
      e.preventDefault();  // 阻止表单默认提交行为

      // 测试账号登录
      if (this.loginForm.username === this.testAccount.username && this.loginForm.password === this.testAccount.password) {
        const token = 'test-token';  // 模拟的 token
        localStorage.setItem('authToken', token);
        this.$router.push({ name: 'dashboard' });  // 登录成功后跳转
        return;
      }

      // 远程接口登录逻辑
      /*
      axios.post('/api/login', this.loginForm)
        .then(response => {
          const token = response.data.token;
          localStorage.setItem('authToken', token);  // 存储 token
          this.$router.push({ name: 'dashboard' });  // 跳转到后台
        })
        .catch(error => {
          this.$message.error('用户名或密码错误');
        });
      */
    },
  },
};
</script>

<style scoped>
/* 设置整个页面背景 */
.login-background {
  background-image: url('@/assets/trees.jpg'); /* 背景图片路径 */
  background-size: cover;
  background-position: center;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 登录框样式 */
.login-container {
  width: 360px;
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.8); /* 半透明白色背景 */
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
}

/* 标题样式 */
h1 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 30px;
  color: #001529;
}

/* 按钮样式 */
.login-form-button {
  width: 100%;
  height: 40px;
  font-size: 16px;
}

/* 控制表单项标签 */
.ant-form-item-label {
  font-size: 14px;
  color: #555;
}

/* 表单项间距 */
.ant-form-item {
  margin-bottom: 20px;
}
</style>
