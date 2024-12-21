<template>
  <a-layout class="dashboard">
    <a-layout-content>
      <div class="dashboard-container">
        <h1>欢迎来到控制面板</h1>
        <!-- 综合统计卡片 -->
        <a-row gutter="16" justify="space-around" style="margin-top: 20px;">
          <a-col :span="6">
            <a-card class="card" title="当前员工数量" bordered>
              <a-statistic :value="employeeCount + ' 人'" />
            </a-card>
          </a-col>

          <a-col :span="6">
            <a-card class="card" title="部门数量" bordered>
              <a-statistic :value="departmentCount + ' 个'" />
            </a-card>
          </a-col>

          <a-col :span="6">
            <a-card class="card" title="出勤率" bordered>
              <a-statistic :value="attendanceRate + ' %'" />
            </a-card>
          </a-col>

          <a-col :span="6">
            <a-card class="card" title="请假统计" bordered>
              <a-statistic :value="leaveRequests + ' 个'" />
            </a-card>
          </a-col>
        </a-row>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
import axios from 'axios';

export default {
  name: 'DashboardPage',
  data() {
    return {
      // 默认数据为空，后续通过接口获取
      employeeCount: 0, // 当前员工数量
      departmentCount: 0, // 部门数量
      attendanceRate: 0, // 出勤率
      leaveRequests: 0, // 请假统计
    };
  },
  mounted() {
    this.fetchDashboardData(); // 组件加载时获取数据
  },
  methods: {
    // 获取后台数据
    fetchDashboardData() {
      // 尝试从API获取数据
      axios.get('/api/dashboard')
        .then(response => {
          const data = response.data;
          // 直接将获取到的值赋值给对应的变量
          this.employeeCount = data.employeeCount;
          this.departmentCount = data.departmentCount;
          this.attendanceRate = data.attendanceRate;
          this.leaveRequests = data.leaveRequests;
        })
        .catch(error => {
          console.error('获取数据失败:', error);
        });
    },
  },
};
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  color: #001529;
  font-size: 24px;
  font-weight: bold;
  text-align: center;
}

.card {
  text-align: center;
  background-color: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.a-row {
  margin-top: 20px;
}
</style>
