<template>
  <a-layout class="attendance-management">
    <a-layout-content>
      <div class="attendance-container">
        <h1>考勤管理</h1>

        <!-- 新增考勤记录按钮 -->
        <a-button type="primary" @click="showAddModal">添加考勤记录</a-button>

        <!-- 考勤记录表格 -->
        <a-table
          :columns="columns"
          :data-source="attendanceRecords"
          row-key="id"
          pagination
        >
          <template #action="{ record }">
            <a-button type="link" @click="showEditModal(record)">编辑</a-button>
            <a-button type="link" danger @click="confirmDelete(record.id)">删除</a-button>
          </template>
        </a-table>

        <!-- 添加考勤记录 Modal -->
        <a-modal
          v-model:visible="isAddModalVisible"
          title="添加考勤记录"
          @ok="addAttendance"
          @cancel="cancelAddModal"
        >
          <a-form :form="addForm" label-col="{ span: 4 }">
            <a-form-item label="员工 ID">
              <a-input v-model:value="addForm.employeeId" />
            </a-form-item>
            <a-form-item label="考勤日期">
              <a-date-picker v-model:value="addForm.attendanceDate" />
            </a-form-item>
            <a-form-item label="出勤状态">
              <a-select v-model:value="addForm.attendanceStatus">
                <a-select-option value="present">出勤</a-select-option>
                <a-select-option value="absent">缺勤</a-select-option>
                <a-select-option value="late">迟到</a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
        </a-modal>

        <!-- 编辑考勤记录 Modal -->
        <a-modal
          v-model:visible="isEditModalVisible"
          title="编辑考勤记录"
          @ok="editAttendance"
          @cancel="cancelEditModal"
        >
          <a-form :form="editForm" label-col="{ span: 4 }">
            <a-form-item label="员工 ID">
              <a-input v-model:value="editForm.employeeId" />
            </a-form-item>
            <a-form-item label="考勤日期">
              <a-date-picker v-model:value="editForm.attendanceDate" />
            </a-form-item>
            <a-form-item label="出勤状态">
              <a-select v-model:value="editForm.attendanceStatus">
                <a-select-option value="present">出勤</a-select-option>
                <a-select-option value="absent">缺勤</a-select-option>
                <a-select-option value="late">迟到</a-select-option>
              </a-select>
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
  name: 'AttendanceManagement',
  data() {
    return {
      // 考勤记录列表
      attendanceRecords: [],
      // 表格列定义
      columns: [
        {
          title: '员工 ID',
          dataIndex: 'employeeId',
          key: 'employeeId',
        },
        {
          title: '考勤日期',
          dataIndex: 'attendanceDate',
          key: 'attendanceDate',
        },
        {
          title: '出勤状态',
          dataIndex: 'attendanceStatus',
          key: 'attendanceStatus',
        },
        {
          title: '操作',
          dataIndex: 'action',
          key: 'action',
          scopedSlots: { customRender: 'action' },
        },
      ],
      // 添加考勤记录 Modal
      isAddModalVisible: false,
      addForm: {
        employeeId: '',
        attendanceDate: '',
        attendanceStatus: 'present',
      },
      // 编辑考勤记录 Modal
      isEditModalVisible: false,
      editForm: {
        id: '',
        employeeId: '',
        attendanceDate: '',
        attendanceStatus: 'present',
      },
    };
  },
  methods: {
    // 获取考勤记录列表
    fetchAttendanceRecords() {
      axios.get('/api/attendance')
        .then(response => {
          this.attendanceRecords = response.data;
        })
        .catch(error => {
          console.error('获取考勤记录失败:', error);
          this.$message.error('获取考勤记录失败');
        });
    },
    // 显示添加考勤记录的 Modal
    showAddModal() {
      this.isAddModalVisible = true;
    },
    // 取消添加考勤记录
    cancelAddModal() {
      this.isAddModalVisible = false;
      this.addForm = {
        employeeId: '',
        attendanceDate: '',
        attendanceStatus: 'present',
      };
    },
    // 添加考勤记录
    addAttendance() {
      axios.post('/api/attendance', this.addForm)
        .then(() => {
          this.fetchAttendanceRecords();  // 更新考勤记录列表
          this.cancelAddModal();          // 关闭 Modal
          this.$message.success('考勤记录添加成功');
        })
        .catch(error => {
          console.error('添加考勤记录失败:', error);
          this.$message.error('添加考勤记录失败');
        });
    },
    // 显示编辑考勤记录的 Modal
    showEditModal(record) {
      this.editForm = { ...record };
      this.isEditModalVisible = true;
    },
    // 取消编辑考勤记录
    cancelEditModal() {
      this.isEditModalVisible = false;
      this.editForm = {
        id: '',
        employeeId: '',
        attendanceDate: '',
        attendanceStatus: 'present',
      };
    },
    // 编辑考勤记录
    editAttendance() {
      axios.put(`/api/attendance/${this.editForm.id}`, this.editForm)
        .then(() => {
          this.fetchAttendanceRecords();  // 更新考勤记录列表
          this.cancelEditModal();         // 关闭 Modal
          this.$message.success('考勤记录编辑成功');
        })
        .catch(error => {
          console.error('编辑考勤记录失败:', error);
          this.$message.error('编辑考勤记录失败');
        });
    },
    // 删除考勤记录
    deleteAttendance(id) {
      axios.delete(`/api/attendance/${id}`)
        .then(() => {
          this.fetchAttendanceRecords();  // 更新考勤记录列表
          this.$message.success('考勤记录删除成功');
        })
        .catch(error => {
          console.error('删除考勤记录失败:', error);
          this.$message.error('删除考勤记录失败');
        });
    },
  },
  mounted() {
    this.fetchAttendanceRecords();  // 页面加载时获取考勤记录列表
  },
};
</script>

<style scoped>
.attendance-container {
  padding: 24px;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  min-height: 400px;
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
  margin-right: 8px;
}

.a-modal {
  .ant-form-item {
    margin-bottom: 16px;
  }
}

.a-button[type="primary"] {
  background-color: #1890ff;
  border-color: #1890ff;
}

.a-button[type="danger"] {
  background-color: #ff4d4f;
  border-color: #ff4d4f;
  color: #fff;
}
</style>
