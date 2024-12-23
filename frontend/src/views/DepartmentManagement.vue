<template>
  <a-layout class="department-management">
    <a-layout-content>
      <div class="department-container">
        <h1>部门管理</h1>

        <!-- 新增部门按钮 -->
        <a-button type="primary" @click="showAddModal">添加部门</a-button>

        <!-- 部门列表 -->
        <a-table
          :columns="columns"
          :data-source="departments"
          row-key="id"
          pagination
        >
          <template v-slot:action="{ record }">
            <a-button @click="showEditModal(record)">编辑</a-button>
            <a-button type="danger" @click="deleteDepartment(record.id)">删除</a-button>
          </template>
        </a-table>

        <!-- 添加部门 Modal -->
        <a-modal
          v-model:visible="isAddModalVisible"
          title="添加部门"
          @ok="addDepartment"
          @cancel="cancelAddModal"
        >
          <a-form :form="addForm" label-col="{ span: 4 }">
            <a-form-item label="部门名称">
              <a-input v-model:value="addForm.departmentName" />
            </a-form-item>
          </a-form>
        </a-modal>

        <!-- 编辑部门 Modal -->
        <a-modal
          v-model:visible="isEditModalVisible"
          title="编辑部门"
          @ok="editDepartment"
          @cancel="cancelEditModal"
        >
          <a-form :form="editForm" label-col="{ span: 4 }">
            <a-form-item label="部门名称">
              <a-input v-model:value="editForm.departmentName" />
            </a-form-item>
          </a-form>
        </a-modal>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
import axios from 'axios';

export default {
  name: 'DepartmentManagement',
  data() {
    return {
      // 部门列表
      departments: [],
      // 表格列定义
      columns: [
        {
          title: '部门名称',
          dataIndex: 'departmentName',
          key: 'departmentName',
        },
        {
          title: '操作',
          dataIndex: 'action',
          key: 'action',
          scopedSlots: { customRender: 'action' },  // 操作列自定义渲染
        },
      ],
      // 添加部门 Modal
      isAddModalVisible: false,
      addForm: {
        departmentName: '',
      },
      // 编辑部门 Modal
      isEditModalVisible: false,
      editForm: {
        id: '',
        departmentName: '',
      },
    };
  },
  methods: {
    // 获取部门列表
    fetchDepartments() {
      axios.get('/api/departments')
        .then(response => {
          this.departments = response.data;
        })
        .catch(error => {
          console.error('获取部门列表失败:', error);
          this.$message.error('获取部门列表失败');
        });
    },
    // 显示添加部门的 Modal
    showAddModal() {
      this.isAddModalVisible = true;
    },
    // 取消添加部门
    cancelAddModal() {
      this.isAddModalVisible = false;
      this.addForm.departmentName = '';
    },
    // 添加部门
    addDepartment() {
      axios.post('/api/departments', this.addForm)
        .then(() => {
          this.fetchDepartments();  // 更新部门列表
          this.cancelAddModal();    // 关闭 Modal
          this.$message.success('部门添加成功');
        })
        .catch(error => {
          console.error('添加部门失败:', error);
          this.$message.error('添加部门失败');
        });
    },
    // 显示编辑部门的 Modal
    showEditModal(record) {
      this.editForm = { ...record };
      this.isEditModalVisible = true;
    },
    // 取消编辑部门
    cancelEditModal() {
      this.isEditModalVisible = false;
      this.editForm = { id: '', departmentName: '' };
    },
    // 编辑部门
    editDepartment() {
      axios.put(`/api/departments/${this.editForm.id}`, this.editForm)
        .then(() => {
          this.fetchDepartments();  // 更新部门列表
          this.cancelEditModal();   // 关闭 Modal
          this.$message.success('部门编辑成功');
        })
        .catch(error => {
          console.error('编辑部门失败:', error);
          this.$message.error('编辑部门失败');
        });
    },
    // 删除部门
    deleteDepartment(id) {
      axios.delete(`/api/departments/${id}`)
        .then(() => {
          this.fetchDepartments();  // 更新部门列表
          this.$message.success('部门删除成功');
        })
        .catch(error => {
          console.error('删除部门失败:', error);
          this.$message.error('删除部门失败');
        });
    },
  },
  mounted() {
    this.fetchDepartments();  // 页面加载时获取部门列表
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
  color: #001529;
}

.a-table {
  margin-top: 20px;
}

.a-button {
  margin-right: 10px;
}
</style>
