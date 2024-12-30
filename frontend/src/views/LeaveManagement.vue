<template>
  <a-layout class="leave-management">
    <a-layout-content>
      <div class="leave-container">
        <h1>请假管理</h1>

        <!-- 新增请假记录按钮 -->
        <a-button type="primary" @click="showAddModal">申请请假</a-button>

        <!-- 请假记录表格 -->
        <a-table :columns="columns" :data-source="leaveRecords" row-key="id" pagination>
          <template #bodyCell="{ column, record }">
            <span v-if="column.dataIndex === 'action'">
              <!-- 编辑按钮 -->
              <a-button type="link" @click="showEditModal(record)">编辑</a-button>
              <!-- 审批按钮 -->
              <a-button type="link" @click="showApprovalModal(record)">审批</a-button>
              <!-- 删除按钮 -->
              <a-button type="link" danger @click="deleteLeave(record.id)">删除</a-button>
            </span>
          </template>
        </a-table>

        <!-- 添加请假记录弹窗 -->
        <a-modal
          v-model:visible="isAddModalVisible"
          title="申请请假"
          @ok="addLeave"
          @cancel="cancelAddModal"
        >
          <a-form :form="addForm" layout="vertical">
            <a-form-item label="员工 ID" :name="'employeeId'">
              <a-input v-model:value="addForm.employeeId" />
            </a-form-item>
            <a-form-item label="请假类型" :name="'leaveType'">
              <a-select v-model:value="addForm.leaveType">
                <a-select-option value="sick">病假</a-select-option>
                <a-select-option value="personal">事假</a-select-option>
                <a-select-option value="vacation">年假</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="开始日期" :name="'startDate'">
              <a-date-picker v-model:value="addForm.startDate" />
            </a-form-item>
            <a-form-item label="结束日期" :name="'endDate'">
              <a-date-picker v-model:value="addForm.endDate" />
            </a-form-item>
            <a-form-item label="请假理由" :name="'reason'">
              <a-input v-model:value="addForm.reason" />
            </a-form-item>
          </a-form>
        </a-modal>

        <!-- 编辑请假记录弹窗 -->
        <a-modal
          v-model:visible="isEditModalVisible"
          title="编辑请假记录"
          @ok="editLeave"
          @cancel="cancelEditModal"
        >
          <a-form :form="editForm" layout="vertical">
            <a-form-item label="员工 ID" :name="'employeeId'">
              <a-input v-model:value="editForm.employeeId" disabled />
            </a-form-item>
            <a-form-item label="请假类型" :name="'leaveType'">
              <a-select v-model:value="editForm.leaveType">
                <a-select-option value="sick">病假</a-select-option>
                <a-select-option value="personal">事假</a-select-option>
                <a-select-option value="vacation">年假</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="开始日期" :name="'startDate'">
              <a-date-picker v-model:value="editForm.startDate" />
            </a-form-item>
            <a-form-item label="结束日期" :name="'endDate'">
              <a-date-picker v-model:value="editForm.endDate" />
            </a-form-item>
            <a-form-item label="请假理由" :name="'reason'">
              <a-input v-model:value="editForm.reason" />
            </a-form-item>
          </a-form>
        </a-modal>

        <!-- 审批请假记录弹窗 -->
        <a-modal
          v-model:visible="isApprovalModalVisible"
          title="审批请假"
          @ok="approveLeave"
          @cancel="cancelApprovalModal"
        >
          <a-form :form="approvalForm" layout="vertical">
            <a-form-item label="审批状态" :name="'status'">
              <a-select v-model:value="approvalForm.status">
                <a-select-option value="approved">批准</a-select-option>
                <a-select-option value="rejected">拒绝</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="审批备注" :name="'comment'">
              <a-input v-model:value="approvalForm.comment" />
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
  name: 'LeaveManagement',
  setup() {
    // 响应式数据
    const leaveRecords = ref([]);
    const isAddModalVisible = ref(false);
    const isEditModalVisible = ref(false);
    const isApprovalModalVisible = ref(false);
    const addForm = ref({
      employeeId: '',
      leaveType: '',
      startDate: null,
      endDate: null,
      reason: ''
    });
    const editForm = ref({
      id: '',
      employeeId: '',
      leaveType: '',
      startDate: null,
      endDate: null,
      reason: ''
    });
    const approvalForm = ref({
      id: '',
      status: '',
      comment: ''
    });

    // 表格列配置
    const columns = [
      { title: '员工 ID', dataIndex: 'employeeId' },
      { title: '请假类型', dataIndex: 'leaveType' },
      { title: '请假日期', dataIndex: 'leaveDate' },
      { title: '审批状态', dataIndex: 'status' },
      { title: '操作', dataIndex: 'action' }
    ];

    // 获取请假记录
    const fetchLeaveRecords = async () => {
      try {
        const response = await axios.get('/api/leaverequests');
        leaveRecords.value = response.data;
      } catch (error) {
        message.error('获取请假记录失败');
      }
    };

    // 显示新增请假记录弹窗
    const showAddModal = () => {
      addForm.value = { employeeId: '', leaveType: '', startDate: null, endDate: null, reason: '' };
      isAddModalVisible.value = true;
    };

    // 取消新增请假记录弹窗
    const cancelAddModal = () => {
      isAddModalVisible.value = false;
    };

    // 添加请假记录
    const addLeave = async () => {
      try {
        await axios.post('/api/leaverequests', addForm.value);
        message.success('请假记录提交成功');
        fetchLeaveRecords(); // 刷新请假记录列表
        cancelAddModal();
      } catch (error) {
        message.error('提交请假记录失败');
      }
    };

    // 显示编辑请假记录弹窗
    const showEditModal = (record) => {
      editForm.value = { ...record };
      isEditModalVisible.value = true;
    };

    // 取消编辑请假记录弹窗
    const cancelEditModal = () => {
      isEditModalVisible.value = false;
    };

    // 编辑请假记录
    const editLeave = async () => {
      try {
        await axios.put(`/api/leaverequests/${editForm.value.id}`, editForm.value);
        message.success('请假记录编辑成功');
        fetchLeaveRecords(); // 刷新请假记录列表
        cancelEditModal();
      } catch (error) {
        message.error('编辑请假记录失败');
      }
    };

    // 显示审批请假记录弹窗
    const showApprovalModal = (record) => {
      approvalForm.value = { id: record.id, status: record.status, comment: '' };
      isApprovalModalVisible.value = true;
    };

    // 取消审批请假记录弹窗
    const cancelApprovalModal = () => {
      isApprovalModalVisible.value = false;
    };

    // 审批请假记录
    const approveLeave = async () => {
      try {
        if (approvalForm.value.status === 'approved') {
          message.success('审批成功');
        } else {
          message.error('审批失败');
        }
      } catch (error) {
        message.error('审批失败');
      }
    };

    // 删除请假记录
    const deleteLeave = async (id) => {
      try {
        await axios.delete(`/api/leaverequests/${id}`);
        message.success('请假记录删除成功');
        fetchLeaveRecords(); // 刷新请假记录列表
      } catch (error) {
        message.error('删除请假记录失败');
      }
    };

    // 页面加载时获取请假记录
    onMounted(() => {
      fetchLeaveRecords();
    });

    return {
      leaveRecords,
      isAddModalVisible,
      isEditModalVisible,
      isApprovalModalVisible,
      addForm,
      editForm,
      approvalForm,
      columns,
      showAddModal,
      cancelAddModal,
      addLeave,
      showEditModal,
      cancelEditModal,
      editLeave,
      showApprovalModal,
      cancelApprovalModal,
      approveLeave,
      deleteLeave
    };
  }
};
</script>

<style scoped>
.leave-container {
  padding: 24px;
  background: #fff;
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

.a-modal .ant-form-item {
  margin-bottom: 16px;
}
</style>
