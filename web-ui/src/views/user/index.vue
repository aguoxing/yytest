<template>
  <div class="app-container">

    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="handleAdd"> 新增 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain :disabled="multiple" @click="handleDelete"> 删除 </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
          <el-form-item label="姓名" prop="username">
            <el-input
                v-model="queryParams.username"
                placeholder="请输入姓名"
                @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">搜索</el-button>
            <el-button @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-table border v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="username" :show-overflow-tooltip="true" />
      <el-table-column label="年龄" align="center" prop="age" :show-overflow-tooltip="true" />
      <el-table-column label="性别" align="center" prop="gender">
        <template #default="scope">
          {{ getDictData(scope.row.gender)}}
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" prop="phoneNumber" :show-overflow-tooltip="true" />
      <el-table-column label="详细地址" align="center" prop="address" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" @click="handleUpdate(scope.row)"> 修改 </el-button>
          <el-button link type="danger" @click="handleDelete(scope.row)"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
        style="padding-top: 10px"
        v-show="total > 0"
        :total="total"
        :layout="pageLayout"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getUserList"
    />

    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" @close="closeDialog" append-to-body>
      <el-form ref="userFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名称" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number style="width: 100%" :min="1" :max="120" v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
            <el-option
              v-for="item in genderOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <elui-china-area-dht v-model="form.addressCode" @change="addressChange" style="width: 100%"></elui-china-area-dht>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="closeDialog">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { addUser, deleteUser, getUser, listUser, updateUser } from '@/api/user'
import { nextTick, onMounted, reactive, ref, toRefs} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import { EluiChinaAreaDht } from 'elui-china-area-dht'
import pagination from "@/components/Pagination"

const ids = ref([])
const single = ref(true)
const multiple = ref(true)

// 验证手机的规则
const checkMobile = (rule, value, callback) => {
  // 手机号正则表达式
  const regMobile = /^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/
  if (regMobile.test(value)) {
    // 合法的手机号
    return callback()
  }
  callback(new Error('请输入合法的手机号'))
}

const genderOptions = [
  {
    value: 0,
    label: '未知',
  },
  {
    value: 1,
    label: '男',
  },
  {
    value: 2,
    label: '女',
  },
  ]

const userList = ref([])
const loading = ref(true)
const total = ref(0)
const pageLayout = ref('prev, pager, next')

const userFormRef = ref<ElForm>(null)
const queryForm = ref<ElForm>(null)

const addressStr = ref("")

const data = reactive({
  dialog: {
    visible: false,
    title: ''
  } ,
  form: {
    gender: 0
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10
  },
  rules: {
    username: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
    gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
    age: [{ required: true, message: '年龄不能为空', trigger: 'blur' }],
    phoneNumber: [{ required: true, message: '联系电话不能为空'},{
      validator: checkMobile,
      trigger: 'blur'
    }],
    address: [{ required: true, message: '地址不能为空', trigger: 'change' }]
  }
})

const { dialog, form, queryParams, rules } = toRefs(data)

const getUserList = () => {
  loading.value = true
  listUser(queryParams.value).then(res => {
    loading.value = false
    userList.value = res.rows
    total.value = res.total
  })
}

/** 多选框选中数据 */
const handleSelectionChange = (selection: any) => {
  ids.value = selection.map((item: any) => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

const closeDialog = () => {
  dialog.value.visible = false
  userFormRef.value?.resetFields()
  userFormRef.value?.clearValidate()
}

const handleAdd = () => {
  dialog.value.visible = true
  dialog.value.title = '新增用户'
}

const handleUpdate = (row: { [key: string]: any }) => {
  const userId = row.id || ids.value
  getUser(userId).then(response => {
    dialog.value.visible = true
    dialog.value.title = '修改用户'
    let addressCode = []
    if (response.data.addressCode !== null) {
      addressCode = response.data.addressCode.split(",")
    }
    nextTick(() => {
      form.value = response.data
      form.value.addressCode = addressCode
    })
  })
}

const handleDelete = (row: { [key: string]: any }) => {
  const userIds = row.id || ids.value;
  ElMessageBox
      .confirm('是否确认删除参数编号为"' + userIds + '"的数据项？')
      .then(() => {
        return deleteUser(userIds)
      })
      .then(() => {
        getUserList()
        ElMessage({
          message: '删除成功',
          type: 'success',
        })
      })
      .catch(() => {})
}

const chinaData = new EluiChinaAreaDht.ChinaArea().chinaAreaflat
const addressChange = (e) => {
  const d1 = chinaData[e[0]]
  const d2 = chinaData[e[1]]
  const d3 = chinaData[e[2]]
  addressStr.value = d1.label+"-"+d2.label+"-"+d3.label;
  form.value.address = addressStr.value;
}

const submitForm = () => {
  form.value.addressCode = form.value.addressCode.toString();
  userFormRef.value.validate((valid: any) => {
    if (valid) {
      if (form.value.id != undefined) {
        updateUser(form.value).then(() => {
          ElMessage({
            message: '修改成功',
            type: 'success',
          })
          closeDialog()
          getUserList()
        })
      } else {
        addUser(form.value).then(() => {
          ElMessage({
            message: '新增成功',
            type: 'success',
          })
          closeDialog()
          getUserList()
        })
      }
    }
  })
}

const getDictData = (data) => {
  for (var i = 0; i < genderOptions.length; i++) {
    if (genderOptions[i].value == data) {
      return genderOptions[i].label;
    }
  }
}

const handleQuery = () => {
    queryParams.value.pageNum = 1
    getUserList()
}

const resetQuery = () => {
  queryForm.value?.resetFields()
  queryForm.value?.clearValidate()
  getUserList()
}

onMounted(() => {
  getUserList()
})
</script>

<style scoped>
.list-container {
  display: flex;
  flex-direction: column;
}
</style>
