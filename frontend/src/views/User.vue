<template>
  <div class="user-page">
    <div class="user-info">
      <h1>用户信息</h1>
      <div class="info-item">
        <label>用户名:</label>
        <span v-if="!isEditing">{{ userInfo.username }}</span>
        <input v-if="isEditing" type="text" v-model="editableUserInfo.username" />
      </div>
      <div class="info-item">
        <label>电子邮件:</label>
        <span v-if="!isEditing">{{ userInfo.email }}</span>
        <input v-if="isEditing" type="email" v-model="editableUserInfo.email" />
      </div>
      <div class="info-item">
        <label>角色:</label>
        <span v-if="!isEditing">{{ userInfo.role }}</span>
        <input v-if="isEditing" type="text" v-model="editableUserInfo.role" />
      </div>
      <div class="info-item">
        <label>部门:</label>
        <span>{{ userInfo.departmentName }}</span>
      </div>

      <h2>考勤记录</h2>
      <div v-for="attendance in userInfo.attendances" :key="attendance.id" class="info-item">
        <label>{{ attendance.attendanceDate }}:</label>
        <span>{{ attendance.attendanceStatus }}</span>
      </div>

      <h2>薪资信息</h2>
      <div class="info-item">
        <label>基础薪资:</label>
        <span>{{ userInfo.salary.baseSalary }}</span>
      </div>
      <div class="info-item">
        <label>奖金:</label>
        <span>{{ userInfo.salary.bonus }}</span>
      </div>
      <div class="info-item">
        <label>福利:</label>
        <span>{{ userInfo.salary.benefits }}</span>
      </div>

      <h2>请假记录</h2>
      <div v-for="leaveRequest in userInfo.leaveRequests" :key="leaveRequest.id" class="info-item">
        <label>{{ leaveRequest.leaveDate }}:</label>
        <span>{{ leaveRequest.leaveType }} - {{ leaveRequest.leaveStatus }}</span>
      </div>

      <button v-if="!isEditing" @click="toggleEdit" class="edit-button">修改信息</button>
      <button v-if="isEditing" @click="saveChanges" class="save-button">保存修改</button>
      <button v-if="isEditing" @click="cancelEdit" class="cancel-button">取消</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserPage',
  data() {
    return {
      isEditing: false, // 控制是否处于编辑模式
      userInfo: {
        username: '',
        email: '',
        role: '',
        departmentName: '',
        attendances: [],
        salary: {
          baseSalary: 0,
          bonus: 0,
          benefits: 0
        },
        leaveRequests: []
      },
      editableUserInfo: {
        username: '',
        email: '',
        role: ''
      }
    };
  },
  created() {
    this.getUserInfo();
  },
  methods: {
    // 获取用户信息
    async getUserInfo() {
      try {
        const response = await axios.get('/api/user/info', {
          params: { userId: 1 } // 假设用户ID是1
        });
        this.userInfo = response.data;
        this.editableUserInfo = { ...this.userInfo }; // 初始化可编辑信息
      } catch (error) {
        console.error('获取用户信息失败:', error);
      }
    },
    // 切换到编辑模式
    toggleEdit() {
      this.isEditing = true;
      this.editableUserInfo = { ...this.userInfo }; // 填充当前数据
    },
    // 保存修改
    async saveChanges() {
      try {
        await axios.put('/api/user/info', this.editableUserInfo, {
          params: { userId: 1 } // 假设用户ID是1
        });

        // 更新成功后，保存修改的数据并退出编辑模式
        this.userInfo = { ...this.editableUserInfo };
        this.isEditing = false;
      } catch (error) {
        console.error('保存修改失败:', error);
      }
    },
    // 取消编辑
    cancelEdit() {
      this.isEditing = false;
      this.editableUserInfo = { ...this.userInfo }; // 取消修改恢复原始数据
    }
  }
};
</script>

<style scoped>
.user-page {
  padding: 20px;
  font-family: Arial, sans-serif;
}

.user-info {
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 24px;
  color: #333;
}

h2 {
  margin-top: 20px;
  color: #333;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #e0e0e0;
}

label {
  font-weight: bold;
}

span {
  color: #777;
}

input {
  padding: 8px;
  width: 100%;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.edit-button,
.save-button,
.cancel-button {
  margin-top: 20px;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.edit-button {
  background-color: #4CAF50;
  color: white;
}

.edit-button:hover {
  background-color: #45a049;
}

.save-button {
  background-color: #007bff;
  color: white;
}

.save-button:hover {
  background-color: #0056b3;
}

.cancel-button {
  background-color: #f44336;
  color: white;
}

.cancel-button:hover {
  background-color: #d32f2f;
}
</style>
