<template>
  <a-table :columns="columns" :data-source="data" :row-selection="rowSelection">
    <template #bodyCell="{column, text, record}">
      <template v-if="column.dataIndex === 'icon'">
        <DynamicIcon v-if="text" :type="text"/>
        <span v-else>-</span>
      </template>
      <template v-if="column.dataIndex === 'operate'">
        <template v-if="record.type === 0">
          <a-space :size="20">
            <a @click.prevent.stop="_ => onAddMenuClick(record.id)">新增</a>
            <a @click.prevent.stop="_ => onUpdateMenuClick(record.id,record.pid)">修改</a>
            <a @click.prevent.stop="_ => onDeleteMenuClick(record.id)">删除</a>
          </a-space>

        </template>
        <template v-else>
          <a-space :size="20">
            <a @click.prevent.once.stop="_ => onUpdateMenuClick(record.id,record.pid)">修改</a>
            <a @click.prevent.once.stop="_ => onDeleteMenuClick(record.id)">删除</a>
          </a-space>
        </template>
      </template>
    </template>
  </a-table>

  <a-modal
      v-model:open="addOrUpdateModelVisible"
      :title="addOrUpdateFormState.id ? '修改' : '新增'"
      :width="800"
      centered
      @ok="addOrUpdateModelVisible = false">
    <div style="display: flex;justify-content: center;align-items: center">
      <a-form :model="addOrUpdateFormState"
              label-align="left"
              ref="addOrUpdateFormRef"
              :label-col="{span: 7}">
        <a-form-item required style="width: 500px;" label="菜单类型" name="type">
          <a-radio-group v-model:value="addOrUpdateFormState.type">
            <a-radio :value="0">菜单</a-radio>
            <a-radio :value="1">按钮</a-radio>
            <a-radio :value="2">接口</a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item required style="width: 500px;" label="名称" name="name">
          <a-input v-model:value="addOrUpdateFormState.name"/>
        </a-form-item>

        <a-form-item style="width: 500px;" label="上级菜单" name="pid">
          <a-tree-select
              v-model:value="addOrUpdateFormState.pid"
              show-search
              style="width: 100%"
              :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
              placeholder="Please select"
              allow-clear
              tree-default-expand-all
              :tree-data="selectMenusData"
              tree-node-filter-prop="label"/>
        </a-form-item>

        <a-form-item style="width: 500px;" label="路由" name="url">
          <a-input v-model:value="addOrUpdateFormState.url"/>
        </a-form-item>

        <a-form-item style="width: 500px;" label="排序" name="sort">
          <a-input-number v-model:value="addOrUpdateFormState.sort"/>
        </a-form-item>

        <a-form-item required style="width: 500px;" label="打开方式" name="openStyle">
          <a-radio-group v-model:value="addOrUpdateFormState.openStyle">
            <a-radio v-for="item in OpenStyleConst.toList()" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item required style="width: 500px;" label="授权标识" name="authority">
          <a-input v-model:value="addOrUpdateFormState.authority"/>
        </a-form-item>

        <a-form-item required style="width: 500px;" label="图标" name="icon">
          <a-input v-model:value="addOrUpdateFormState.icon"/>
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
</template>

<script setup>
import {reactive, ref} from 'vue';
import {fetchMenuListApi} from "@/api/index.js";
import {OpenStyleConst} from "@/utils/constant/MenuConst.js";
import {message} from "ant-design-vue";
import DynamicIcon from "@/components/DynamicIcon.vue";
import {menuDataToAntTree} from "@/utils/ant-design-tools.js";

const columns = [
  {
    title: '名称',
    dataIndex: 'name',
  },
  {
    title: '图标',
    dataIndex: 'icon',
  },
  {
    title: '打开方式',
    dataIndex: 'openStyle',
    customRender: ({text, record, index, column}) => OpenStyleConst.getNameByCode(text)
  },
  {
    title: '排序',
    dataIndex: 'sort',
  },
  {
    title: '路由',
    dataIndex: 'url',
  },
  {
    title: '授权标识',
    dataIndex: 'authority',
  },
  {
    title: '操作',
    dataIndex: 'operate'
  }
];
const addOrUpdateModelVisible = ref(false);
const data = ref([]);
const rowSelection = ref({
  checkStrictly: false,
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
  },
  onSelect: (record, selected, selectedRows) => {
    console.log(record, selected, selectedRows);
  },
  onSelectAll: (selected, selectedRows, changeRows) => {
    console.log(selected, selectedRows, changeRows);
  },
});

const addOrUpdateFormRef = ref()
const addOrUpdateFormState = reactive({})
const selectMenusData = ref([])

const onAddMenuClick = async (id) => {
  const [res, err] = await fetchMenuListApi({type: 0})
  if (err) {
    return message.warn(err?.msg)
  }
  selectMenusData.value = menuDataToAntTree(res);

  addOrUpdateModelVisible.value = true;
  addOrUpdateFormState.pid = id
  addOrUpdateFormState.id = undefined
}


const onUpdateMenuClick = async (id, pid) => {
  // TODO fetchMenuInfo
  const [res, err] = await fetchMenuListApi({type: 0})
  if (err) {
    return message.warn(err?.msg)
  }
  selectMenusData.value = menuDataToAntTree(res);

  addOrUpdateModelVisible.value = true;
  addOrUpdateFormState.id = id
  addOrUpdateFormState.pid = pid === 0 ? undefined : pid
}
const onDeleteMenuClick = (id) => {

}

const fetchData = async _ => {
  const [res, err] = await fetchMenuListApi()
  if (!err) {
    data.value = res
    return
  }
  message.warn(err?.msg)
}

fetchData()
</script>
