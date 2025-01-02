<template>
  <div class="user-page">
    <div class="user-info">
      <h1>用户信息</h1>
      <div class="info-item">
        <label>用户名:</label>
        <span>{{ userInfo.username }}</span>
      </div>
      <div class="info-item">
        <label>角色:</label>
        <span>{{ userInfo.role }}</span>
      </div>
      <div class="info-item">
        <label>部门:</label>
        <span>{{ userInfo.departmentName }}</span>
      </div>

      <h2>薪资信息</h2>
      <div class="info-item">
        <label>基础薪资:</label>
        <span>{{ userInfo.baseSalary }}</span>
      </div>
      <div class="info-item">
        <label>奖金:</label>
        <span>{{ userInfo.bonus }}</span>
      </div>
      <div class="info-item">
        <label>福利:</label>
        <span>{{ userInfo.benefits }}</span>
      </div>

      <h2>请假记录</h2>
      <div v-for="leaveRequest in userInfo.leaveRequests" :key="leaveRequest.id" class="info-item">
        <label>{{ leaveRequest.leaveDate }}:</label>
        <span>{{ leaveRequest.leaveType }} - {{ leaveRequest.leaveStatus }}</span>
      </div>
      <h2>考勤记录</h2>
      <div v-for="attendance in userInfo.attendances" :key="attendance.id" class="info-item">
        <label>{{ attendance.attendanceDate }}:</label>
        <span>{{ attendance.attendanceStatus }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'UserPage',
  data() {
    return {
      userInfo: {
        username: '', // 用户名将从 localStorage 中获取
        role: '',
        employeeName: '',
        departmentName: '',
        baseSalary: 0,
        bonus: 0,
        benefits: 0,
        attendances: [],
        leaveRequests: []
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
        const username = localStorage.getItem('username');
        if (username) {
          // 使用已存储的用户名来获取用户详细信息
          const response = await axios.post('/api/account/details', {
            username: username
          });
          this.userInfo = response.data;
        }
      } catch (error) {
        console.error('获取用户信息失败:', error);
      }
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
</style>
