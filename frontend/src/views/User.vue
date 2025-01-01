<template>
  <div class="user-page">
    <div class="user-info">
      <h1>欢迎来到用户页面</h1>
      <div class="info-item">
        <label>您的角色是:</label>
        <span>{{ role }}</span>
      </div>
      <div class="info-item">
        <label>用户名:</label>
        <span>{{ username }}</span>
      </div>
      <div class="info-item">
        <label>电子邮件:</label>
        <span>{{ email }}</span>
      </div>
      <div class="info-item">
        <label>最后登录时间:</label>
        <span>{{ lastLoginTime }}</span>
      </div>
      
      <button @click="isEditing = !isEditing" class="edit-button">
        {{ isEditing ? '取消' : '修改信息' }}
      </button>
      
      <div v-if="isEditing" class="edit-form">
        <div class="form-item">
          <label for="username">用户名:</label>
          <input type="text" id="username" v-model="editableUsername" />
        </div>
        <div class="form-item">
          <label for="email">电子邮件:</label>
          <input type="email" id="email" v-model="editableEmail" />
        </div>
        <div class="form-item">
          <label for="role">角色:</label>
          <input type="text" id="role" v-model="editableRole" />
        </div>
        <button @click="saveChanges" class="save-button">保存修改</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserPage',
  data() {
    return {
      role: localStorage.getItem('userRole') || '未知角色',
      username: localStorage.getItem('username') || '未知用户',
      email: localStorage.getItem('email') || '未知邮箱',
      lastLoginTime: localStorage.getItem('lastLoginTime') || '未知时间',
      isEditing: false, // 控制是否在编辑模式
      editableUsername: localStorage.getItem('username') || '未知用户',
      editableEmail: localStorage.getItem('email') || '未知邮箱',
      editableRole: localStorage.getItem('userRole') || '未知角色',
    };
  },
  methods: {
    saveChanges() {
      localStorage.setItem('username', this.editableUsername);
      localStorage.setItem('email', this.editableEmail);
      localStorage.setItem('userRole', this.editableRole);
      
      // 更新页面中的数据
      this.username = this.editableUsername;
      this.email = this.editableEmail;
      this.role = this.editableRole;
      
      // 退出编辑模式
      this.isEditing = false;
    }
  },
};
</script>

<style scoped>
.user-page {
  padding: 30px;
  font-family: Arial, sans-serif;
}

.user-info {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  color: #333;
  font-size: 24px;
  margin-bottom: 20px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #e0e0e0;
}

label {
  font-weight: bold;
  color: #555;
}

span {
  color: #777;
  font-size: 16px;
}

.info-item:last-child {
  border-bottom: none;
}

.edit-button {
  margin-top: 20px;
  padding: 10px 15px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.edit-button:hover {
  background-color: #45a049;
}

.edit-form {
  margin-top: 20px;
  padding: 20px;
  background-color: #f1f1f1;
  border-radius: 8px;
}

.form-item {
  margin-bottom: 15px;
}

input {
  padding: 8px;
  width: 100%;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.save-button {
  padding: 10px 15px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.save-button:hover {
  background-color: #45a049;
}
</style>
