<template>
  <a-button type="primary">
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
      <template v-if="column === 'operate'">

      </template>
    </template>
  </a-table>
</template>

<script setup>
import {yyyy_mm_dd_hh_mm_ss} from "@/utils/tools.js";
import {fetchRoleListApi} from "@/api/index.js";
import {useTable} from "@/hooks/useTable.js";

const columns = [
  {
    title: '名称',
    dataIndex: 'name',
  },

  {
    title: '编码',
    dataIndex: 'roleCode',
  },

  {
    title: '备注',
    dataIndex: 'remark',
  },

  {
    title: '创建时间',
    dataIndex: 'createTime',
    customRender: ({text, record, index, column}) => yyyy_mm_dd_hh_mm_ss(text)
  },

  {
    title: '操作',
    dataIndex: 'operate',
  },
]
const {
  dataSource,
  loading,
  pagination,
  handleTableChange,
  fetchTableData
} = useTable({
  fetchApi: fetchRoleListApi,
})

fetchTableData()

</script>

<style scoped>

</style>
