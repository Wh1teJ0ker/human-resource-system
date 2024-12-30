<template>
  <a-layout class="attendance-management">
    <a-layout-content>
      <div class="attendance-container">
        <h1>考勤管理</h1>

        <!-- 添加考勤记录按钮 -->
        <a-button type="primary" @click="showAddModal">添加考勤记录</a-button>

        <!-- 考勤记录表格 -->
        <a-table :columns="columns" :data-source="attendanceRecords" row-key="id" pagination>
          <template #bodyCell="{ column, record }">
            <span v-if="column.dataIndex === 'action'">
              <!-- 编辑按钮 -->
              <a-button type="link" @click="showEditModal(record)">编辑</a-button>
              <!-- 删除按钮 -->
              <a-button type="link" danger @click="deleteAttendance(record.id)">删除</a-button>
            </span>
          </template>
        </a-table>

        <!-- 添加考勤记录弹窗 -->
        <a-modal
          v-model:visible="isAddModalVisible"
          title="添加考勤记录"
          @ok="addAttendance"
          @cancel="cancelAddModal"
        >
          <a-form layout="vertical">
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

        <!-- 编辑考勤记录弹窗 -->
        <a-modal
          v-model:visible="isEditModalVisible"
          title="编辑考勤记录"
          @ok="editAttendance"
          @cancel="cancelEditModal"
        >
          <a-form layout="vertical">
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
import { ref, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import dayjs from 'dayjs';
import axios from 'axios';

export default {
  name: 'AttendanceManagement',
  setup() {
    const attendanceRecords = ref([]);
    const isAddModalVisible = ref(false);
    const addForm = ref({
      employeeId: '',
      attendanceDate: '',
      attendanceStatus: 'present',
    });
    const isEditModalVisible = ref(false);
    const editForm = ref({
      id: '',
      employeeId: '',
      attendanceDate: '',
      attendanceStatus: 'present',
    });

    const columns = [
      {
        title: '员工 ID',
        dataIndex: 'employeeId',
      },
      {
        title: '考勤日期',
        dataIndex: 'attendanceDate',
      },
      {
        title: '出勤状态',
        dataIndex: 'attendanceStatus',
      },
      {
        title: '操作',
        dataIndex: 'action',
      },
    ];

    const fetchAttendanceRecords = async () => {
      try {
        const response = await axios.get('/api/attendance');
        attendanceRecords.value = response.data.map((record) => ({
          ...record,
          attendanceDate: dayjs(record.attendanceDate).format('YYYY-MM-DD'),
        }));
      } catch (error) {
        message.error('获取考勤记录失败');
      }
    };

    const showAddModal = () => {
      isAddModalVisible.value = true;
    };

    const cancelAddModal = () => {
      isAddModalVisible.value = false;
      addForm.value = { employeeId: '', attendanceDate: '', attendanceStatus: 'present' };
    };

    const addAttendance = async () => {
      try {
        const payload = {
          ...addForm.value,
          attendanceDate: dayjs(addForm.value.attendanceDate).format('YYYY-MM-DD'),
        };
        await axios.post('/api/attendance', payload);
        fetchAttendanceRecords();
        cancelAddModal();
        message.success('考勤记录添加成功');
      } catch (error) {
        message.error('添加考勤记录失败');
      }
    };

    const showEditModal = (record) => {
      editForm.value = {
        ...record,
        attendanceDate: dayjs(record.attendanceDate),
      };
      isEditModalVisible.value = true;
    };

    const cancelEditModal = () => {
      isEditModalVisible.value = false;
      editForm.value = { id: '', employeeId: '', attendanceDate: '', attendanceStatus: 'present' };
    };

    const editAttendance = async () => {
      try {
        const payload = {
          ...editForm.value,
          attendanceDate: dayjs(editForm.value.attendanceDate).format('YYYY-MM-DD'),
        };
        await axios.put(`/api/attendance/${editForm.value.id}`, payload);
        fetchAttendanceRecords();
        cancelEditModal();
        message.success('考勤记录编辑成功');
      } catch (error) {
        message.error('编辑考勤记录失败');
      }
    };

    const deleteAttendance = async (id) => {
      try {
        await axios.delete(`/api/attendance/${id}`);
        fetchAttendanceRecords();
        message.success('考勤记录删除成功');
      } catch (error) {
        message.error('删除考勤记录失败');
      }
    };

    onMounted(() => {
      fetchAttendanceRecords();
    });

    return {
      attendanceRecords,
      isAddModalVisible,
      addForm,
      isEditModalVisible,
      editForm,
      columns,
      showAddModal,
      cancelAddModal,
      addAttendance,
      showEditModal,
      cancelEditModal,
      editAttendance,
      deleteAttendance,
    };
  },
};
</script>

<style scoped>
.attendance-container {
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
