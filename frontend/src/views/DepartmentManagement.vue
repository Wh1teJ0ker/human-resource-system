<template>
  <a-layout class="department-management">
    <a-layout-content>
      <div class="department-container">
        <h1>部门管理</h1>

        <!-- 搜索框 -->
        <a-input-search
          v-model:value="searchKeyword"
          placeholder="请输入部门名称"
          enter-button="搜索"
          @search="searchDepartments"
          style="width: 300px; margin-bottom: 20px;"
        />

        <!-- 添加部门按钮 -->
        <a-button type="primary" @click="showAddModal">添加部门</a-button>

        <!-- 部门列表 -->
        <a-table :columns="columns" :data-source="filteredDepartments" row-key="id">
          <!-- 使用 v-slot:bodyCell 来定义操作列 -->
          <template #bodyCell="{ column, record }">
            <span v-if="column.dataIndex === 'action'">
              <!-- 编辑按钮 -->
              <a-button type="link" @click="editDepartment(record)">编辑</a-button>
              <!-- 删除按钮 -->
              <a-button type="link" danger @click="deleteDepartment(record.id)">删除</a-button>
            </span>
          </template>
        </a-table>

        <!-- 添加/编辑部门弹窗 -->
        <a-modal
          v-model:visible="isModalVisible"
          :title="modalTitle"
          @ok="handleOk"
          @cancel="handleCancel"
        >
          <a-form :form="form" layout="vertical">
            <a-form-item label="部门名称" :name="'departmentName'">
              <a-input v-model:value="form.departmentName" />
            </a-form-item>
          </a-form>
        </a-modal>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
import { ref, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import axios from 'axios';

export default {
  name: 'DepartmentManagement',
  setup() {
    // 响应式数据
    const departments = ref([]);
    const filteredDepartments = ref([]); // 过滤后的部门列表
    const searchKeyword = ref(''); // 搜索关键词
    const isModalVisible = ref(false);
    const form = ref({
      id: null,
      departmentName: '',
    });
    const modalTitle = ref('添加部门');

    // 表格列配置
    const columns = [
      {
        title: '部门名称',
        dataIndex: 'departmentName',
      },
      {
        title: '操作',
        dataIndex: 'action',
      },
    ];

    // 获取部门列表
    const fetchDepartments = async () => {
      try {
        const response = await axios.get('/api/departments');
        departments.value = response.data;
        filteredDepartments.value = response.data; // 初始化过滤后的部门数据
      } catch (error) {
        message.error('获取部门列表失败');
      }
    };

    // 搜索部门
    const searchDepartments = () => {
      if (!searchKeyword.value) {
        filteredDepartments.value = departments.value; // 如果没有关键词，显示所有部门
      } else {
        filteredDepartments.value = departments.value.filter((department) => {
          return department.departmentName
            .toLowerCase()
            .includes(searchKeyword.value.toLowerCase());
        });
      }
    };

    // 显示添加部门弹窗
    const showAddModal = () => {
      modalTitle.value = '添加部门';
      form.value = { id: null, departmentName: '' };
      isModalVisible.value = true;
    };

    // 显示编辑部门弹窗
    const editDepartment = (record) => {
      modalTitle.value = '编辑部门';
      form.value = { ...record }; // 填充表单数据
      isModalVisible.value = true;
    };

    // 处理弹窗确认
    const handleOk = async () => {
      try {
        if (form.value.id) {
          // 编辑部门
          await axios.put(`/api/departments/${form.value.id}`, {
            departmentName: form.value.departmentName,
          });
          message.success('部门信息更新成功');
        } else {
          // 添加部门
          await axios.post('/api/departments', {
            departmentName: form.value.departmentName,
          });
          message.success('部门添加成功');
        }
        fetchDepartments(); // 刷新部门列表
        handleCancel();
      } catch (error) {
        message.error('操作失败');
      }
    };

    // 处理弹窗取消
    const handleCancel = () => {
      isModalVisible.value = false;
    };

    // 删除部门
    const deleteDepartment = async (id) => {
      try {
        await axios.delete(`/api/departments/${id}`);
        message.success('部门删除成功');
        fetchDepartments(); // 刷新部门列表
      } catch (error) {
        message.error('删除部门失败');
      }
    };

    // 页面加载时获取部门列表
    onMounted(() => {
      fetchDepartments();
    });

    return {
      departments,
      filteredDepartments,
      searchKeyword,
      isModalVisible,
      form,
      modalTitle,
      columns,
      showAddModal,
      editDepartment,
      handleOk,
      handleCancel,
      deleteDepartment,
      searchDepartments,
    };
  },
};
</script>

<style scoped>
.department-container {
  padding: 20px;
  background: #fff;
  min-height: 360px;
}

h1 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #001529;
}
</style>
