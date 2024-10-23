<template>
  <a-button type="primary" @click="onAddClick">
    新增
  </a-button>

  <a-table
      style="margin-top: 10px"
      :loading="loading"
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      @change="handleTableChange">
    <template #bodyCell="{column, text, record}">
      <template v-if="column.dataIndex === 'operate'">
        <a-space :size="20">
          <a v-auth="['sys:role:add','sys:role:update','sys:role:info']"
             @click.prevent.stop="onUpdateClick(record.id)">修改</a>
          <a-popconfirm title="Del Role?" @confirm="onDeleteClick(record.id)">
            <a v-auth="'sys:role:del'" @click.prevent.stop="">删除</a>
          </a-popconfirm>
        </a-space>
      </template>
    </template>
  </a-table>
</template>

<script setup>

import {yyyy_mm_dd_hh_mm_ss} from "@/utils/tools.js";
import {useTable} from "@/hooks/useTable.js";
import {fetchUserListApi} from "@/api/index.js";
import {Gender, Status} from "@/utils/constant/UserConst.js";

const columns = [
  {
    title: '用户名',
    dataIndex: 'username',
  },

  {
    title: '姓名',
    dataIndex: 'realName',
  },

  {
    title: '性别',
    dataIndex: 'gender',
    customRender: ({text, record, index, column}) => Gender.getNameByCode(text)
  },

  {
    title: '手机号',
    dataIndex: 'mobile'
  },

  {
    title: '状态',
    dataIndex: 'status',
    customRender:({text, record, index, column}) => Status.getNameByCode(text)
  },

  {
    title: '创建时间',
    dataIndex: 'createTime',
    customRender: ({text, record, index, column}) => yyyy_mm_dd_hh_mm_ss(text)
  },

  {
    title: '操作',
    dataIndex: 'operate',
  }
]

const {
  dataSource,
  loading,
  pagination,
  handleTableChange,
  fetchTableData
} = useTable({
  fetchApi: fetchUserListApi,
})

const onAddClick = _ => {

}

const onUpdateClick = id => {

}

const onDeleteClick = id => {

}

fetchTableData()
</script>

<style scoped>

</style>
