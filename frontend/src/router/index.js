import { createRouter, createWebHistory } from 'vue-router';
//import LoginPage from '../views/LoginPage.vue';  // 导入登录页
//import RegisterPage from '../views/RegisterPage.vue';  // 导入注册页
import Dashboard from '../views/Dashboard.vue';  // 导入后台首页
import AccountSettings from '../views/AccountSettings.vue';  // 新的账户设置页面
import EmployeeManagement from '../views/EmployeeManagement.vue';
import DepartmentManagement from '../views/DepartmentManagement.vue';
import AttendanceManagement from '../views/AttendanceManagement.vue';
import LeaveManagement from '../views/LeaveManagement.vue';
import SalaryManagement from '../views/SalaryManagement.vue';
import UserPage from '../views/User.vue';

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginPage.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/RegisterPage.vue'),
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: Dashboard,
    meta: { role: 'ADMIN', requiresAuth: true }, // 只有 ADMIN 角色能访问
  },
  {
    path: '/user',
    name: 'user',
    component: UserPage,
    meta: { role: 'USER', requiresAuth: true }, // 只有 USER 角色能访问
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
  const token = localStorage.getItem('authToken'); // 获取 token
  const userRole = localStorage.getItem('userRole'); // 获取角色

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!token) {
      // 如果没有 token，跳转到登录页
      return next({ name: 'login' });
    }

    // 校验角色是否匹配
    if (to.meta.role && to.meta.role !== userRole) {
      alert('无权限访问该页面');
      return next({ name: 'login' }); // 无权限跳转到登录页
    }
  }

  next();
});

export default router;
