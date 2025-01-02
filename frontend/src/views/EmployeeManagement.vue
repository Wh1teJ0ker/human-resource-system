<template>
  <a-layout class="employee-management">
    <a-layout-content>
      <div class="employee-container">
        <h1>员工管理</h1>

        <!-- 搜索框 -->
        <a-input-search
          v-model:value="searchKeyword"
          placeholder="请输入员工姓名或职位"
          enter-button="搜索"
          @search="searchEmployees"
          style="width: 300px; margin-bottom: 20px;"
        />

        <!-- 添加员工按钮 -->
        <a-button type="primary" @click="showAddModal">添加员工</a-button>

        <!-- 员工列表 -->
        <a-table :columns="columns" :data-source="filteredEmployees" row-key="id">
          <!-- 使用 v-slot:bodyCell 来定义操作列 -->
          <template #bodyCell="{ column, record }">
            <span v-if="column.dataIndex === 'action'">
              <!-- 编辑按钮 -->
              <a-button type="link" @click="editEmployee(record)">编辑</a-button>
              <!-- 删除按钮 -->
              <a-button type="link" danger @click="deleteEmployee(record.id)">删除</a-button>
            </span>
          </template>
        </a-table>

        <!-- 添加/编辑员工弹窗 -->
        <a-modal
          v-model:visible="isModalVisible"
          :title="modalTitle"
          @ok="handleOk"
          @cancel="handleCancel"
        >
          <a-form :form="form" layout="vertical">
            <a-form-item label="姓名" :name="'name'">
              <a-input v-model:value="form.name" />
            </a-form-item>
            <a-form-item label="职位" :name="'position'">
              <a-input v-model:value="form.position" />
            </a-form-item>
            <a-form-item label="部门" :name="'department'">
              <a-input v-model:value="form.department" />
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
  name: 'EmployeeManagement',
  setup() {
    // 响应式数据
    const employees = ref([]);
    const filteredEmployees = ref([]);
    const searchKeyword = ref(''); // 搜索关键词
    const isModalVisible = ref(false);
    const form = ref({
      id: null,
      name: '',
      position: '',
      department: ''
    });
    const modalTitle = ref('添加员工');

    // 表格列配置
    const columns = [
      {
        title: '员工 ID',
        dataIndex: 'id',
      },
      {
        title: '姓名',
        dataIndex: 'name',
      },
      {
        title: '职位',
        dataIndex: 'position',
      },
      {
        title: '部门',
        dataIndex: 'department',
      },
      {
        title: '操作',
        dataIndex: 'action',
      },
    ];

    // 获取员工列表
    const fetchEmployees = async () => {
      try {
        const response = await axios.get('/api/employees');
        employees.value = response.data;
        filteredEmployees.value = response.data; // 初始化过滤后的员工数据
      } catch (error) {
        message.error('获取员工列表失败');
      }
    };

    // 搜索员工
    const searchEmployees = () => {
      if (!searchKeyword.value) {
        filteredEmployees.value = employees.value; // 如果没有关键词，显示所有员工
      } else {
        filteredEmployees.value = employees.value.filter((employee) => {
          return (
            employee.name.toLowerCase().includes(searchKeyword.value.toLowerCase()) ||
            employee.position.toLowerCase().includes(searchKeyword.value.toLowerCase())
          );
        });
      }
    };

    // 显示添加员工弹窗
    const showAddModal = () => {
      modalTitle.value = '添加员工';
      form.value = { id: null, name: '', position: '', department: '' };
      isModalVisible.value = true;
    };

    // 显示编辑员工弹窗
    const editEmployee = (record) => {
      modalTitle.value = '编辑员工';
      form.value = { ...record };  // 填充表单数据
      isModalVisible.value = true;
    };

    // 处理弹窗确认
    const handleOk = async () => {
      try {
        if (form.value.id) {
          // 编辑员工
          await axios.put(`/api/employees/${form.value.id}`, {
            name: form.value.name,
            position: form.value.position,
            department: form.value.department,
          });
          message.success('员工信息更新成功');
        } else {
          // 添加员工
          await axios.post('/api/employees', {
            name: form.value.name,
            position: form.value.position,
            department: form.value.department,
          });
          message.success('员工添加成功');
        }
        fetchEmployees(); // 刷新员工列表
        handleCancel();
      } catch (error) {
        message.error('操作失败');
      }
    };

    // 处理弹窗取消
    const handleCancel = () => {
      isModalVisible.value = false;
    };

    // 删除员工
    const deleteEmployee = async (id) => {
      try {
        await axios.delete(`/api/employees/${id}`);
        message.success('员工删除成功');
        fetchEmployees(); // 刷新员工列表
      } catch (error) {
        message.error('删除员工失败');
      }
    };

    // 页面加载时获取员工列表
    onMounted(() => {
      fetchEmployees();
    });

    return {
      employees,
      filteredEmployees,
      searchKeyword,
      isModalVisible,
      form,
      modalTitle,
      columns,
      showAddModal,
      editEmployee,
      handleOk,
      handleCancel,
      deleteEmployee,
      searchEmployees,
    };
  },
};
</script>

<style scoped>
.employee-container {
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

.a-input-search {
  margin-bottom: 20px;
}
</style>
