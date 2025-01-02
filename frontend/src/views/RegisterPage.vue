<template>
  <div class="register-page">
    <div class="register-container">
      <h2>注册账号</h2>
      <form @submit.prevent="register">
        <div class="form-item">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="username" placeholder="请输入用户名" required />
        </div>
        <div class="form-item">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="password" placeholder="请输入密码" required />
        </div>
        <button type="submit" class="register-button">立即注册</button>
      </form>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    </div>

    <!-- 自定义弹窗 -->
    <div v-if="showSuccessModal" class="modal-overlay">
      <div class="modal">
        <h3>注册成功！</h3>
        <p>即将跳转到登录页面。</p>
        <button @click="goToLogin">确认</button>
      </div>
    </div>

    <div v-if="showErrorModal" class="modal-overlay">
      <div class="modal">
        <h3>注册失败</h3>
        <p>{{ errorMessage }}</p>
        <button @click="closeErrorModal">关闭</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "RegisterPage",
  data() {
    return {
      username: '',
      password: '',
      errorMessage: '', // 错误信息
      showSuccessModal: false, // 控制注册成功弹窗显示
      showErrorModal: false, // 控制错误弹窗显示
    };
  },
  methods: {
    async register() {
      // 检查输入字段是否为空
      if (!this.username || !this.password) {
        this.errorMessage = '用户名和密码不能为空!';
        this.showErrorModal = true;
        return;
      }

      const requestData = {
        username: this.username,
        password: this.password
      };

      try {
        console.log('发送注册请求:', requestData);
        // 发送 POST 请求到后端 API
        const response = await axios.post('/api/account/register', requestData, {
          headers: {
            'Content-Type': 'application/json'
          }
        });

        console.log('注册响应:', response.data);

        // 后端返回200表示成功
        if (response.status === 200 && response.data.code === '200') {
          this.showSuccessModal = true; // 显示注册成功弹窗
          this.errorMessage = ''; // 清除错误信息
        } else {
          this.errorMessage = response.data.message || '注册失败，请重试!';
          this.showErrorModal = true; // 显示错误弹窗
        }
      } catch (error) {
        console.error('注册请求失败:', error);
        this.errorMessage = '注册请求失败，请稍后再试!';
        this.showErrorModal = true; // 显示错误弹窗
      }
    },

    goToLogin() {
      this.$router.push('/login'); // 注册成功后跳转到登录页面
    },

    closeErrorModal() {
      this.showErrorModal = false; // 关闭错误弹窗
    }
  }
};
</script>

<style scoped>
.register-page {
  height: 90vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ffffff;
}

.register-container {
  width: 360px;
  padding: 32px;
  background: #ffffff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  text-align: center;
}

h2 {
  font-size: 24px;
  margin-bottom: 20px;
  color: #333333;
}

.form-item {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  font-size: 14px;
  color: #666666;
  margin-bottom: 6px;
}

input {
  width: 100%;
  padding: 12px 14px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
  transition: border-color 0.2s, box-shadow 0.2s;
}

input:focus {
  outline: none;
  border-color: #409eff;
  box-shadow: 0 0 6px rgba(64, 158, 255, 0.3);
}

.register-button {
  width: 100%;
  padding: 12px 0;
  font-size: 16px;
  color: #ffffff;
  background-color: #409eff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.register-button:hover {
  background-color: #66b1ff;
}

.register-button:active {
  background-color: #3a8ee6;
}

.error-message {
  color: red;
  margin-top: 10px;
}

/* 弹窗样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  max-width: 400px;
  width: 100%;
}

.modal h3 {
  font-size: 20px;
  margin-bottom: 10px;
}

.modal p {
  margin-bottom: 20px;
  font-size: 16px;
}

.modal button {
  padding: 8px 16px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.modal button:hover {
  background-color: #66b1ff;
}

.modal button:active {
  background-color: #3a8ee6;
}
</style>
