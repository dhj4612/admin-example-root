<template>
  <a-button v-auth="'sys:role:add'" type="primary" @click="onAddClick">
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
          <a v-auth="['sys:role:update','sys:role:info']"
             @click.prevent.stop="onUpdateClick(record.id)">修改</a>
          <a-popconfirm title="Del Role?" @confirm="onDeleteClick(record.id)">
            <a v-auth="'sys:role:del'" @click.prevent.stop="">删除</a>
          </a-popconfirm>
        </a-space>
      </template>
    </template>
  </a-table>

  <AddOrUpdate :open="addOrUpdateModelOpen"
               :update-id="updateId"
               @onModelClose="addOrUpdateModelOpen = false"
               @onRefresh="fetchTableData"
  />
</template>

<script setup>
import {yyyy_mm_dd_hh_mm_ss} from "@/utils/tools.js";
import {fetchRolePageApi, roleDelApi} from "@/api/index.js";
import {useTable} from "@/hooks/useTable.js";
import AddOrUpdate from "@/views/sys/role/component/AddOrUpdate.vue";
import {ref} from "vue";
import {message} from "ant-design-vue";

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
  fetchApi: fetchRolePageApi,
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

const onDeleteClick = async id => {
  const [_, e] = await roleDelApi({id})
  if (e) {
    message.warn(e.msg)
    return Promise.reject()
  }
  await fetchTableData()
  return Promise.resolve()
}

fetchTableData()

</script>

<style scoped>

</style>
