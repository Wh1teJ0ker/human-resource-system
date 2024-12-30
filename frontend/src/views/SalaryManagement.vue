<template>
  <a-layout class="salary-management">
    <a-layout-content>
      <div class="salary-container">
        <h1>薪资管理</h1>

        <!-- 新增薪资记录按钮 -->
        <a-button type="primary" @click="showAddModal">添加薪资记录</a-button>

        <!-- 薪资记录表格 -->
        <a-table
          :columns="columns"
          :data-source="salaryRecords"
          row-key="id"
          pagination
        >
          <template #bodyCell="{ column, record }">
            <span v-if="column.dataIndex === 'action'">
              <!-- 编辑按钮 -->
              <a-button type="link" @click="showEditModal(record)">编辑</a-button>
              <!-- 删除按钮 -->
              <a-button type="link" danger @click="deleteSalary(record.id)">删除</a-button>
            </span>
          </template>
        </a-table>

        <!-- 添加薪资记录 Modal -->
        <a-modal
          v-model:visible="isAddModalVisible"
          title="添加薪资记录"
          @ok="addSalary"
          @cancel="cancelAddModal"
        >
          <a-form :layout="'vertical'">
            <a-form-item label="员工 ID">
              <a-input v-model:value="addForm.employeeId" />
            </a-form-item>
            <a-form-item label="基本薪资">
              <a-input-number v-model:value="addForm.baseSalary" style="width: 100%" />
            </a-form-item>
            <a-form-item label="奖金">
              <a-input-number v-model:value="addForm.bonus" style="width: 100%" />
            </a-form-item>
            <a-form-item label="其他福利">
              <a-input-number v-model:value="addForm.benefits" style="width: 100%" />
            </a-form-item>
          </a-form>
        </a-modal>

        <!-- 编辑薪资记录 Modal -->
        <a-modal
          v-model:visible="isEditModalVisible"
          title="编辑薪资记录"
          @ok="editSalary"
          @cancel="cancelEditModal"
        >
          <a-form :layout="'vertical'">
            <a-form-item label="员工 ID">
              <a-input v-model:value="editForm.employeeId" disabled />
            </a-form-item>
            <a-form-item label="基本薪资">
              <a-input-number v-model:value="editForm.baseSalary" style="width: 100%" />
            </a-form-item>
            <a-form-item label="奖金">
              <a-input-number v-model:value="editForm.bonus" style="width: 100%" />
            </a-form-item>
            <a-form-item label="其他福利">
              <a-input-number v-model:value="editForm.benefits" style="width: 100%" />
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
  name: 'SalaryManagement',
  setup() {
    // 响应式数据
    const salaryRecords = ref([]);
    const isAddModalVisible = ref(false);
    const isEditModalVisible = ref(false);
    const addForm = ref({
      employeeId: '',
      baseSalary: 0,
      bonus: 0,
      benefits: 0,
    });
    const editForm = ref({
      id: '',
      employeeId: '',
      baseSalary: 0,
      bonus: 0,
      benefits: 0,
    });

    // 表格列配置
    const columns = [
      { title: '员工 ID', dataIndex: 'employeeId' },
      { title: '基本薪资', dataIndex: 'baseSalary' },
      { title: '奖金', dataIndex: 'bonus' },
      { title: '其他福利', dataIndex: 'benefits' },
      { title: '操作', dataIndex: 'action' }
    ];

    // 获取薪资记录
    const fetchSalaryRecords = async () => {
      try {
        const response = await axios.get('/api/salaries');
        salaryRecords.value = response.data;
      } catch (error) {
        message.error('获取薪资记录失败');
      }
    };

    // 显示新增薪资记录弹窗
    const showAddModal = () => {
      addForm.value = { employeeId: '', baseSalary: 0, bonus: 0, benefits: 0 };
      isAddModalVisible.value = true;
    };

    // 取消新增薪资记录弹窗
    const cancelAddModal = () => {
      isAddModalVisible.value = false;
    };

    // 添加薪资记录
    const addSalary = async () => {
      try {
        await axios.post('/api/salaries', addForm.value);
        message.success('薪资记录添加成功');
        fetchSalaryRecords(); // 刷新薪资记录列表
        cancelAddModal();
      } catch (error) {
        message.error('添加薪资记录失败');
      }
    };

    // 显示编辑薪资记录弹窗
    const showEditModal = (record) => {
      editForm.value = { ...record };
      isEditModalVisible.value = true;
    };

    // 取消编辑薪资记录弹窗
    const cancelEditModal = () => {
      isEditModalVisible.value = false;
    };

    // 编辑薪资记录
    const editSalary = async () => {
      try {
        await axios.put(`/api/salaries/${editForm.value.id}`, editForm.value);
        message.success('薪资记录编辑成功');
        fetchSalaryRecords(); // 刷新薪资记录列表
        cancelEditModal();
      } catch (error) {
        message.error('编辑薪资记录失败');
      }
    };

    // 删除薪资记录
    const deleteSalary = async (id) => {
      try {
        await axios.delete(`/api/salaries/${id}`);
        message.success('薪资记录删除成功');
        fetchSalaryRecords(); // 刷新薪资记录列表
      } catch (error) {
        message.error('删除薪资记录失败');
      }
    };

    // 页面加载时获取薪资记录
    onMounted(() => {
      fetchSalaryRecords();
    });

    return {
      salaryRecords,
      isAddModalVisible,
      isEditModalVisible,
      addForm,
      editForm,
      columns,
      showAddModal,
      cancelAddModal,
      addSalary,
      showEditModal,
      cancelEditModal,
      editSalary,
      deleteSalary,
    };
  }
};
</script>

<style scoped>
.salary-container {
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

.a-table {
  margin-top: 20px;
}

.a-button {
  margin-right: 10px;
}

.a-modal .ant-form-item {
  margin-bottom: 16px;
}
</style>
