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
      <template v-if="column === 'operate'">
        <a-space :size="20">
          <a @click.prevent.stop="onUpdateClick(column.id)">修改</a>
          <a @click.prevent.stop="onDeleteClick(column.id)">删除</a>
        </a-space>
      </template>
    </template>
  </a-table>

  <AddOrUpdate :open="addOrUpdateModelOpen"
               :update-id="updateId"
               @onModelClose="_ => addOrUpdateModelOpen = false"
  />
</template>

<script setup>
import {yyyy_mm_dd_hh_mm_ss} from "@/utils/tools.js";
import {fetchRoleListApi} from "@/api/index.js";
import {useTable} from "@/hooks/useTable.js";
import AddOrUpdate from "@/views/sys/role/component/AddOrUpdate.vue";
import {ref} from "vue";

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

const addOrUpdateModelOpen = ref(false)

const onAddClick = _ => {
  updateId.value = NaN
  addOrUpdateModelOpen.value = true
}

const updateId = ref(NaN)
const onUpdateClick = id => {
  updateId.value = id
  addOrUpdateModelOpen.value = true
}

const onDeleteClick = id => {

}

fetchTableData()

</script>

<style scoped>

</style>
