<template>
  <a-layout class="login-layout">
    <a-layout-content>
      <div class="login-background">
        <div class="login-container">
          <h1>人力资源后台管理系统</h1>
          <a-form :form="loginForm" @submit.prevent="handleLogin" class="login-form">
            <!-- 用户角色选择 -->
            <a-form-item label="选择角色" :label-col="{ span: 6 }" :wrapper-col="{ span: 14 }">
              <a-select v-model:value="loginForm.role" placeholder="请选择角色">
                <a-select-option value="ADMIN">管理员</a-select-option>
                <a-select-option value="USER">普通用户</a-select-option>
              </a-select>
            </a-form-item>

            <!-- 用户名输入框 -->
            <a-form-item label="用户名" :label-col="{ span: 6 }" :wrapper-col="{ span: 14 }">
              <a-input v-model:value="loginForm.username" placeholder="请输入用户名" />
            </a-form-item>

            <!-- 密码输入框 -->
            <a-form-item label="密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 14 }">
              <a-input v-model:value="loginForm.password" type="password" placeholder="请输入密码" />
            </a-form-item>

            <!-- 登录按钮 -->
            <a-form-item :wrapper-col="{ span: 14, offset: 6 }">
              <a-button type="primary" html-type="submit" class="login-form-button">
                登录
              </a-button>
            </a-form-item>

            <!-- 注册按钮 -->
            <a-form-item :wrapper-col="{ span: 14, offset: 6 }">
              <a-button type="default" class="register-link" @click="goToRegister">
                注册
              </a-button>
            </a-form-item>
          </a-form>
        </div>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginPage',
  data() {
    return {
      loginForm: {
        role: '', // 用户角色
        username: '', // 用户名
        password: '', // 密码
      },
      errorMessage: '', // 错误信息
    };
  },
  methods: {
    async handleLogin() {
      // 校验表单
      if (!this.loginForm.role) {
        this.errorMessage = '请选择角色！';
        return;
      }

      const requestData = {
        username: this.loginForm.username,
        password: this.loginForm.password,
      };

      try {
        console.log('发送登录请求:', requestData);
        // 向后端发送 POST 请求
        const response = await axios.post('/api/account/login', requestData, {
          headers: { 'Content-Type': 'application/json' },
        });

        console.log('登录响应:', response.data);

        // 根据后端返回的 code 判断结果
        if (response.data.code === '200') {
          // 保存 token 和角色到 localStorage
          const role = response.data.message.includes('ADMIN') ? 'ADMIN' : 'USER';
          const token = 'backend-generated-token'; // 如果后端返回 token，可以替换为真实的 token
          localStorage.setItem('authToken', token);
          localStorage.setItem('userRole', role);
          localStorage.setItem('username', this.loginForm.username); 

          // 根据角色跳转到不同页面
          if (role === 'ADMIN') {
            this.$router.push({ name: 'dashboard' }); // 管理员页面
          } else {
            this.$router.push({ name: 'user' }); // 普通用户页面
          }
        } else if (response.data.code === '401') {
          this.errorMessage = '用户名或密码错误！';
        } else {
          this.errorMessage = '服务器错误，请稍后再试！';
        }
      } catch (error) {
        console.error('登录请求失败:', error);
        this.errorMessage = '登录请求失败，请检查网络连接或稍后再试！';
      }
    },
    goToRegister() {
      this.$router.push({ name: 'register' }); // 跳转到注册页面
    },
  },
};
      /*
export default {
  name: 'LoginPage',
  data() {
    return {
      loginForm: {
        role: '', // 角色字段，默认空值
        username: '',
        password: '',
      },
      testAccounts: [
        {
          username: 'admin',
          password: 'admin',
          role: 'ADMIN', // 测试账号管理员角色
        },
        {
          username: 'user',
          password: 'user',
          role: 'USER', // 测试账号普通用户角色
        },
      ],
    };
  },
  methods: {
    // 处理登录
    handleLogin() {
      // 校验是否填写了角色
      if (!this.loginForm.role) {
        alert('请选择角色！');
        return;
      }

      // 测试账号登录逻辑
      const matchedAccount = this.testAccounts.find(
        account =>
          account.username === this.loginForm.username &&
          account.password === this.loginForm.password &&
          account.role === this.loginForm.role
      );

      if (matchedAccount) {
        const token = 'test-token'; // 模拟的 token
        localStorage.setItem('authToken', token);
        localStorage.setItem('userRole', matchedAccount.role); // 存储角色

        // 根据角色跳转到不同的页面
        if (matchedAccount.role === 'ADMIN') {
          this.$router.push({ name: 'dashboard' }); // 管理员跳转到 Dashboard
        } else if (matchedAccount.role === 'USER') {
          this.$router.push({ name: 'user' }); // 普通用户跳转到 User 页面
        }
        return;
      }

      alert('用户名或密码或角色错误！');
    },
    // 跳转到注册页面
    goToRegister() {
      this.$router.push({ name: 'register' });
    },
  },
};      */


      // 远程接口登录逻辑（可以启用以下代码）
      /*
      axios.post('/api/login', this.loginForm)
        .then(response => {
          const token = response.data.token;
          localStorage.setItem('authToken', token);
          this.$router.push({ name: 'dashboard' });
        })
        .catch(error => {
          this.$message.error('用户名或密码错误');
        });
      */
</script>

<style scoped>
/* 背景样式 */
.login-background {
  background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('@/assets/trees.jpg');
  background-size: cover;
  background-position: center;
  height: 90vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 登录框样式 */
.login-container {
  width: 400px;
  padding: 50px 40px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 12px;
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.3);
  text-align: center;
}

/* 标题样式 */
h1 {
  font-size: 28px;
  color: #001529;
  margin-bottom: 30px;
}

/* 表单项样式 */
.ant-form-item-label {
  font-size: 14px;
  color: #555;
}

.ant-form-item {
  margin-bottom: 20px;
}

input {
  border-radius: 8px;
}

input:focus {
  border-color: #409eff;
  box-shadow: 0 0 6px rgba(64, 158, 255, 0.4);
}

/* 登录按钮 */
.login-form-button {
  width: 100%;
  height: 45px;
  font-size: 16px;
  color: #fff;
  background: linear-gradient(90deg, #409eff, #66b1ff);
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}

.login-form-button:hover {
  background: linear-gradient(90deg, #66b1ff, #409eff);
  box-shadow: 0 4px 15px rgba(64, 158, 255, 0.4);
}

.login-form-button:active {
  background: #3a8ee6;
}

/* 注册按钮 */
.register-link {
  width: 100%;
  height: 45px;
  font-size: 16px;
  color: #409eff;
  background-color: #fff;
  border: 1px solid #409eff;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.register-link:hover {
  background-color: #f0f7ff;
}
</style>
