import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '../views/LoginPage.vue';  // 导入登录页
import Dashboard from '../views/Dashboard.vue';  // 导入后台首页
import AccountSettings from '../views/AccountSettings.vue';  // 新的账户设置页面
import EmployeeManagement from '../views/EmployeeManagement.vue';
import DepartmentManagement from '../views/DepartmentManagement.vue';
import AttendanceManagement from '../views/AttendanceManagement.vue';
import LeaveManagement from '../views/LeaveManagement.vue';
import SalaryManagement from '../views/SalaryManagement.vue';

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginPage,  // 登录页
  },
  {
    path: '/',
    name: 'dashboard',
    component: Dashboard,
    meta: { requiresAuth: true },
  },
  {
    path: '/account-settings',
    name: 'account-settings',
    component: AccountSettings,  // 新路由
    meta: { requiresAuth: true },
  },
  {
    path: '/employee-management',
    name: 'employee-management',
    component: EmployeeManagement,
    meta: { requiresAuth: true },
  },
  {
    path: '/department-management',
    name: 'department-management',
    component: DepartmentManagement,
    meta: { requiresAuth: true },
  },
  {
    path: '/attendance-management',
    name: 'attendance-management',
    component: AttendanceManagement,
    meta: { requiresAuth: true },
  },
  {
    path: '/leave-management',
    name: 'leave-management',
    component: LeaveManagement,
    meta: { requiresAuth: true },
  },
  {
    path: '/salary-management',
    name: 'salary-management',
    component: SalaryManagement,
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 全局路由守卫，检查是否已经登录
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('authToken');

  // 如果目标路由需要认证且用户未登录，则重定向到登录页面
  if (to.meta.requiresAuth && !isAuthenticated) {
    next({ name: 'login' });
  } else {
    next();
  }
});

export default router;
