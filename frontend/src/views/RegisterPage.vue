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
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "RegisterPage", // 给组件命名
    data() {
      return {
        username: '',
        password: ''
      };
    },
    methods: {
      register() {
        console.log('注册:', this.username, this.password);
        // 在此处添加注册逻辑（如调用API）
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
    background-color: #ffffff; /* 全白背景 */
  }
  
  .register-container {
    width: 360px;
    padding: 32px;
    background: #ffffff;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 柔和阴影效果 */
    border-radius: 12px;
    text-align: center;
  }
  
  h2 {
    font-size: 24px;
    margin-bottom: 20px;
    color: #333333; /* 深色标题 */
  }
  
  .form-item {
    margin-bottom: 20px;
    text-align: left;
  }
  
  label {
    display: block;
    font-size: 14px;
    color: #666666; /* 灰色标签 */
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
    border-color: #409eff; /* 聚焦时的高亮边框 */
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
    background-color: #66b1ff; /* 按钮悬停效果 */
  }
  
  .register-button:active {
    background-color: #3a8ee6; /* 按钮点击效果 */
  }
  </style>
  