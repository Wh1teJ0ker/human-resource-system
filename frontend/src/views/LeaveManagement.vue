<template>
  <a-layout class="leave-management">
    <a-layout-content>
      <div class="leave-container">
        <h1>请假管理</h1>

        <!-- 新增请假记录按钮 -->
        <a-button type="primary" @click="showAddModal">申请请假</a-button>

        <!-- 请假记录表格 -->
        <a-table
          :columns="columns"
          :data-source="leaveRecords"
          row-key="id"
          pagination
        >
          <template v-slot:action="{ record }">
            <a-button @click="showEditModal(record)">审批</a-button>
            <a-button type="danger" @click="deleteLeave(record.id)">删除</a-button>
          </template>
        </a-table>

        <!-- 添加请假记录 Modal -->
        <a-modal
          v-model:visible="isAddModalVisible"
          title="申请请假"
          @ok="addLeave"
          @cancel="cancelAddModal"
        >
          <a-form :form="addForm" label-col="{ span: 4 }">
            <a-form-item label="员工姓名">
              <a-input v-model:value="addForm.employeeName" />
            </a-form-item>
            <a-form-item label="请假类型">
              <a-select v-model:value="addForm.leaveType">
                <a-select-option value="sick">病假</a-select-option>
                <a-select-option value="personal">事假</a-select-option>
                <a-select-option value="vacation">年假</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="开始日期">
              <a-date-picker v-model:value="addForm.startDate" />
            </a-form-item>
            <a-form-item label="结束日期">
              <a-date-picker v-model:value="addForm.endDate" />
            </a-form-item>
            <a-form-item label="请假理由">
              <a-input v-model:value="addForm.reason" />
            </a-form-item>
          </a-form>
        </a-modal>

        <!-- 编辑请假记录 Modal -->
        <a-modal
          v-model:visible="isEditModalVisible"
          title="审批请假"
          @ok="editLeave"
          @cancel="cancelEditModal"
        >
          <a-form :form="editForm" label-col="{ span: 4 }">
            <a-form-item label="审批状态">
              <a-select v-model:value="editForm.status">
                <a-select-option value="approved">批准</a-select-option>
                <a-select-option value="rejected">拒绝</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="审批备注">
              <a-input v-model:value="editForm.comment" />
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
  name: 'LeaveManagement',
  data() {
    return {
      leaveRecords: [],
      columns: [
        { title: '员工姓名', dataIndex: 'employeeName', key: 'employeeName' },
        { title: '请假类型', dataIndex: 'leaveType', key: 'leaveType' },
        { title: '请假日期', dataIndex: 'leaveDate', key: 'leaveDate' },
        { title: '审批状态', dataIndex: 'status', key: 'status' },
        { title: '操作', dataIndex: 'action', key: 'action', scopedSlots: { customRender: 'action' } },
      ],
      isAddModalVisible: false,
      addForm: {
        employeeName: '',
        leaveType: '',
        startDate: null,
        endDate: null,
        reason: '',
      },
      isEditModalVisible: false,
      editForm: {
        id: '',
        status: '',
        comment: '',
      },
    };
  },
  methods: {
    fetchLeaveRecords() {
      axios.get('/api/leaverequests').then(response => {
        this.leaveRecords = response.data;
      }).catch(error => {
        console.error('获取请假记录失败:', error);
        this.$message.error('获取请假记录失败');
      });
    },
    showAddModal() { this.isAddModalVisible = true; },
    cancelAddModal() {
      this.isAddModalVisible = false;
      this.addForm = { employeeName: '', leaveType: '', startDate: null, endDate: null, reason: '' };
    },
    addLeave() {
      axios.post('/api/leaverequests', this.addForm).then(() => {
        this.fetchLeaveRecords();
        this.cancelAddModal();
        this.$message.success('请假记录提交成功');
      }).catch(error => {
        console.error('提交请假记录失败:', error);
        this.$message.error('提交请假记录失败');
      });
    },
    showEditModal(record) {
      this.editForm = { ...record };
      this.isEditModalVisible = true;
    },
    cancelEditModal() {
      this.isEditModalVisible = false;
      this.editForm = { id: '', status: '', comment: '' };
    },
    editLeave() {
      axios.put(`/api/leaverequests/${this.editForm.id}`, this.editForm).then(() => {
        this.fetchLeaveRecords();
        this.cancelEditModal();
        this.$message.success('请假记录审批成功');
      }).catch(error => {
        console.error('审批请假记录失败:', error);
        this.$message.error('审批请假记录失败');
      });
    },
    deleteLeave(id) {
      axios.delete(`/api/leaverequests/${id}`).then(() => {
        this.fetchLeaveRecords();
        this.$message.success('请假记录删除成功');
      }).catch(error => {
        console.error('删除请假记录失败:', error);
        this.$message.error('删除请假记录失败');
      });
    },
  },
  mounted() { this.fetchLeaveRecords(); },
};
</script>

<style scoped>
.leave-container {
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
