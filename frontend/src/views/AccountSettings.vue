<template>
  <a-layout class="account-settings-layout">
    <a-layout-content style="padding: 24px;">
      <div class="account-settings-container">
        <h1>修改管理员账户</h1>
        <a-form :form="form" @submit="handleSubmit" class="account-form">
          <a-form-item label="用户名" :label-col="{ span: 6 }" :wrapper-col="{ span: 14 }">
            <a-input v-model:value="form.username" placeholder="请输入新的用户名" />
          </a-form-item>

          <a-form-item label="密码" :label-col="{ span: 6 }" :wrapper-col="{ span: 14 }">
            <a-input v-model:value="form.password" type="password" placeholder="请输入新的密码" />
          </a-form-item>

          <a-form-item :wrapper-col="{ span: 14, offset: 6 }">
            <a-button 
              type="primary" 
              html-type="submit" 
              :loading="loading"
              class="account-form-button">
              提交
            </a-button>
          </a-form-item>
        </a-form>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
export default {
  name: 'AccountSettings',
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
      loading: false, // 用于控制按钮的加载状态
    };
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();

      // 简单的表单验证
      if (!this.form.username || !this.form.password) {
        this.$message.error('用户名和密码不能为空');
        return;
      }

      // 设置按钮加载状态
      this.loading = true;

      // 模拟 API 请求（这里可以调用后端接口来保存更改）
      setTimeout(() => {
        // 假设在此处进行后端 API 调用
        // axios.post('/api/account/updatePassword', {
        //   username: this.form.username,
        //   password: this.form.password,
        // }).then(response => {
        //   this.$message.success('管理员账户修改成功');
        // }).catch(error => {
        //   this.$message.error('账户修改失败');
        // });

        // 模拟本地保存用户名
        localStorage.setItem('username', this.form.username);
        this.$message.success('管理员账户修改成功');
        
        // 重置加载状态
        this.loading = false;
      }, 1500); // 模拟延迟，假设后端处理时间为1.5秒
    },
  },
};
</script>

<style scoped>
.account-settings-container {
  width: 400px;
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.account-form-button {
  width: 100%;
}
</style>
