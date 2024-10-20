<template>
  <a-table :columns="columns" :data-source="data" :row-selection="rowSelection">
    <template #bodyCell="{column, text, record}">
      <template v-if="column.dataIndex === 'icon'">
        <DynamicIcon v-if="text" :type="text"/>
        <span v-else>-</span>
      </template>
      <template v-if="column.dataIndex === 'operate'">
        <template v-if="record.type === 0">
          <a-space v-auth="'admin'" :size="20">
            <a @click.prevent.stop="_ => onAddMenuClick(record.id)">新增</a>
            <a @click.prevent.stop="_ => onUpdateMenuClick(record.id,record.pid)">修改</a>
            <a-popconfirm title="Del Menu?" @confirm="onDeleteMenuClick(record.id)">
              <a @click.prevent.stop="">删除</a>
            </a-popconfirm>
          </a-space>

        </template>
        <template v-else>
          <a-space v-auth="'admin'" :size="20">
            <a @click.prevent.stop="_ => onUpdateMenuClick(record.id,record.pid)">修改</a>
            <a-popconfirm title="Del Menu?" @confirm="onDeleteMenuClick(record.id)">
              <a @click.prevent.stop="">删除</a>
            </a-popconfirm>
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
      :confirm-loading="modelConfirmLoading"
      @ok="onModelConfirm">
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
              :tree-data="selectorMenusData"
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

        <a-form-item style="width: 500px;" label="授权标识" name="authority">
          <a-input v-model:value="addOrUpdateFormState.authority"/>
        </a-form-item>

        <a-form-item style="width: 500px;" label="图标" name="icon">
          <a-input v-model:value="addOrUpdateFormState.icon"/>
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
</template>

<script setup>
import {reactive, ref} from 'vue';
import {fetchMenuInfoApi, fetchMenuListApi, menuAddOrUpdateApi, menuDelApi} from "@/api/index.js";
import {OpenStyleConst} from "@/utils/constant/MenuConst.js";
import {message} from "ant-design-vue";
import DynamicIcon from "@/components/DynamicIcon.vue";
import {treeDataConvertByNameKeys} from "@/utils/ant-design-tools.js";

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
const selectorMenusData = ref([])
const modelConfirmLoading = ref(false)

const onModelConfirm = () => {
  addOrUpdateFormRef.value.validate()
      .then(async formState => {
        modelConfirmLoading.value = true
        if (addOrUpdateFormState.id) {
          formState.id = addOrUpdateFormState.id
        }
        const [_, e] = await menuAddOrUpdateApi(formState)
        if (e) {
          message.warn(e?.msg)
        }
        await fetchData()
        modelConfirmLoading.value = false
        addOrUpdateModelVisible.value = false
      })
      .catch(_ => {
      })
}

const onAddMenuClick = async (id) => {
  Object.keys(addOrUpdateFormState).forEach(key => addOrUpdateFormState[key] = undefined)
  const [res, err] = await fetchMenuListApi({type: 0})
  if (err) {
    return message.warn(err?.msg)
  }
  selectorMenusData.value = treeDataConvertByNameKeys(res, {
    keyName: 'value',
    valueName: 'label'
  });

  addOrUpdateFormState.pid = id
  addOrUpdateFormState.id = undefined
  addOrUpdateModelVisible.value = true;
}

const onUpdateMenuClick = async (id, pid) => {
  const [r, e] = await fetchMenuInfoApi({id})
  if (e) {
    return message.warn(err?.msg)
  }
  Object.keys(r).forEach(key => addOrUpdateFormState[key] = r[key])

  const [res, err] = await fetchMenuListApi({type: 0})
  if (err) {
    return message.warn(err?.msg)
  }
  selectorMenusData.value = treeDataConvertByNameKeys(res, {
    keyName: 'value',
    valueName: 'label'
  });

  addOrUpdateFormState.id = id
  addOrUpdateFormState.pid = pid === 0 ? undefined : pid
  addOrUpdateModelVisible.value = true;
}

const onDeleteMenuClick = async (id) => {
  const [_, e] = await menuDelApi({id})
  if (e) {
    message.warn(e?.msg)
    return Promise.reject()
  }
  await fetchData()
  return Promise.resolve()
}

const fetchData = async _ => {
  const [res, err] = await fetchMenuListApi()
  if (!err) {
    data.value = treeDataConvertByNameKeys(res, {valueName: 'name'});
    return
  }
  message.warn(err?.msg)
}

fetchData()
</script>
