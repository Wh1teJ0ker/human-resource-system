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
          <template v-slot:action="{ record }">
            <a-button type="link" @click="showEditModal(record)">编辑</a-button>
            <a-button type="link" danger @click="deleteSalary(record.id)">删除</a-button>
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
            <a-form-item label="员工姓名">
              <a-input v-model:value="addForm.employeeName" />
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
            <a-form-item label="员工姓名">
              <a-input v-model:value="editForm.employeeName" />
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
import axios from "axios";

export default {
  name: "SalaryManagement",
  data() {
    return {
      // 薪资记录列表
      salaryRecords: [],
      // 表格列定义
      columns: [
        {
          title: "员工姓名",
          dataIndex: "employeeName",
          key: "employeeName",
        },
        {
          title: "基本薪资",
          dataIndex: "baseSalary",
          key: "baseSalary",
        },
        {
          title: "奖金",
          dataIndex: "bonus",
          key: "bonus",
        },
        {
          title: "其他福利",
          dataIndex: "benefits",
          key: "benefits",
        },
        {
          title: "操作",
          key: "action",
          scopedSlots: { customRender: "action" },
        },
      ],
      // 添加薪资记录 Modal
      isAddModalVisible: false,
      addForm: {
        employeeName: "",
        baseSalary: 0,
        bonus: 0,
        benefits: 0,
      },
      // 编辑薪资记录 Modal
      isEditModalVisible: false,
      editForm: {
        id: "",
        employeeName: "",
        baseSalary: 0,
        bonus: 0,
        benefits: 0,
      },
    };
  },
  methods: {
    // 获取薪资记录列表
    fetchSalaryRecords() {
      axios
        .get("/api/salaries")
        .then((response) => {
          this.salaryRecords = response.data;
        })
        .catch((error) => {
          console.error("获取薪资记录失败:", error);
          this.$message.error("获取薪资记录失败");
        });
    },
    // 显示添加薪资记录的 Modal
    showAddModal() {
      this.isAddModalVisible = true;
    },
    // 取消添加薪资记录
    cancelAddModal() {
      this.isAddModalVisible = false;
      this.resetAddForm();
    },
    // 重置添加表单
    resetAddForm() {
      this.addForm = {
        employeeName: "",
        baseSalary: 0,
        bonus: 0,
        benefits: 0,
      };
    },
    // 添加薪资记录
    addSalary() {
      axios
        .post("/api/salaries", this.addForm)
        .then((response) => {
          this.fetchSalaryRecords();
          this.cancelAddModal();
          this.$message.success(response.data.message || "薪资记录添加成功");
        })
        .catch((error) => {
          console.error("添加薪资记录失败:", error);
          this.$message.error("添加薪资记录失败");
        });
    },
    // 显示编辑薪资记录的 Modal
    showEditModal(record) {
      this.editForm = { ...record };
      this.isEditModalVisible = true;
    },
    // 取消编辑薪资记录
    cancelEditModal() {
      this.isEditModalVisible = false;
      this.resetEditForm();
    },
    // 重置编辑表单
    resetEditForm() {
      this.editForm = {
        id: "",
        employeeName: "",
        baseSalary: 0,
        bonus: 0,
        benefits: 0,
      };
    },
    // 编辑薪资记录
    editSalary() {
      axios
        .put(`/api/salaries/${this.editForm.id}`, this.editForm)
        .then((response) => {
          this.fetchSalaryRecords();
          this.cancelEditModal();
          this.$message.success(response.data.message || "薪资记录编辑成功");
        })
        .catch((error) => {
          console.error("编辑薪资记录失败:", error);
          this.$message.error("编辑薪资记录失败");
        });
    },
    // 删除薪资记录
    deleteSalary(id) {
      axios
        .delete(`/api/salaries/${id}`)
        .then((response) => {
          this.fetchSalaryRecords();
          this.$message.success(response.data.message || "薪资记录删除成功");
        })
        .catch((error) => {
          console.error("删除薪资记录失败:", error);
          this.$message.error("删除薪资记录失败");
        });
    },
  },
  mounted() {
    this.fetchSalaryRecords(); // 页面加载时获取薪资记录列表
  },
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
</style>
