import Vue from 'vue';
import Router from 'vue-router';

// 导入各个页面组件
import Dashboard from '@/views/Dashboard.vue';
import EmployeeManagement from '@/views/EmployeeManagement.vue';
import DepartmentManagement from '@/views/DepartmentManagement.vue';
import AttendanceManagement from '@/views/AttendanceManagement.vue';
import LeaveManagement from '@/views/LeaveManagement.vue';
import SalaryManagement from '@/views/SalaryManagement.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Dashboard',
      component: Dashboard,
    },
    {
      path: '/employee-management',
      name: 'EmployeeManagement',
      component: EmployeeManagement,
    },
    {
      path: '/department-management',
      name: 'DepartmentManagement',
      component: DepartmentManagement,
    },
    {
      path: '/attendance-management',
      name: 'AttendanceManagement',
      component: AttendanceManagement,
    },
    {
      path: '/leave-management',
      name: 'LeaveManagement',
      component: LeaveManagement,
    },
    {
      path: '/salary-management',
      name: 'SalaryManagement',
      component: SalaryManagement,
    },
  ],
});
