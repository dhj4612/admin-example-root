<template>
  <a-modal :open="open"
           :title="updateId ? '修改' : '新增'"
           :width="800"
           centered
           @cancel="onModelCancel"
           :confirm-loading="modelConfirmLoading"
           @ok="onModelConfirm">
    <div style="display: flex;width:100%;justify-content: center;align-items: center">
      <a-form
          :model="addOrUpdateFormState"
          style="width: 100%"
          label-align="right"
          :label-col="{span: 4}"
          :wrapper-col="{span: 20}"
          ref="addOrUpdateFormRef">
        <a-form-item required label="名称" name="name">
          <a-input v-model:value="addOrUpdateFormState.name"/>
        </a-form-item>
        <a-form-item required label="编码" name="roleCode">
          <a-input v-model:value="addOrUpdateFormState.roleCode"/>
        </a-form-item>

        <a-form-item label="备注" name="remark">
          <a-input v-model:value="addOrUpdateFormState.remark"/>
        </a-form-item>

        <a-form-item label="菜单权限" name="menuIds">
          <a-tree-select
              v-model:value="addOrUpdateFormState.menuIds"
              style="width: 100%"
              :tree-data="selectorMenusData"
              tree-checkable
              allow-clear
              :show-checked-strategy="TreeSelect.SHOW_ALL"
              placeholder="选择菜单权限"
              tree-node-filter-prop="label"
          />
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
</template>

<script setup>
import {reactive, ref, toRaw, watch} from "vue";
import {message, TreeSelect} from "ant-design-vue";
import {addOrUpdateRoleApi, fetchMenuListApi, fetchRoleInfoApi} from "@/api/index.js";
import {collectFullPathTreeIds, treeDataConvertByNameKeys} from "@/utils/ant-design-tools.js";
import _lodash from 'lodash';

const props = defineProps({
  open: {
    type: Boolean,
    default: false
  },
  updateId: {
    type: Number,
    default: NaN
  }
})

const emits = defineEmits(['onModelOk', 'onModelClose', 'onRefresh']);

const modelConfirmLoading = ref(false)
const onModelConfirm = async () => {
  addOrUpdateFormRef.value.validate()
      .then(async formState => {
        modelConfirmLoading.value = true
        if (!Number.isNaN(props.updateId)) {
          formState.id = props.updateId
        }
        if (!formState.menuIds) {
          formState.menuIds = []
        }
        const [_, e] = await addOrUpdateRoleApi(formState)
        if (e) {
          message.warn(e.msg)
        }
        modelConfirmLoading.value = false
        emits('onModelClose')
        emits('onRefresh')
      })
      .catch(_ => {
      })
}
const onModelCancel = _ => emits('onModelClose')

const addOrUpdateFormState = reactive({})
const addOrUpdateFormRef = ref()

const selectorMenusData = ref([]);
let fullSelectMenuIds = undefined;

watch(_ => addOrUpdateFormState.menuIds, _ => {
  // 收集当前选择的节点的完整父级 id 集合，使用 Set 过滤共同的父级
  if (addOrUpdateFormState.menuIds) {
    if (!addOrUpdateFormState.menuIds.length) {
      fullSelectMenuIds = []
      return
    }

    const _menuIds = _lodash.cloneDeep(toRaw(addOrUpdateFormState.menuIds))
    const src = _lodash.cloneDeep(toRaw(selectorMenusData.value))
    const collectFullIds = _menuIds.map(menuId => {
      return collectFullPathTreeIds(menuId, src)
    }).flatMap(id => id);
    fullSelectMenuIds = new Set(collectFullIds)
    console.log(fullSelectMenuIds)
  }
})

watch(_ => props.open, async _ => {
  if (props.open === true) {
    const [r, e] = await fetchMenuListApi()
    if (e) {
      return message.warn(e.msg)
    }
    selectorMenusData.value = treeDataConvertByNameKeys(r, {
      keyName: 'value',
      valueName: 'label'
    });

    Object.keys(addOrUpdateFormState).forEach(key => addOrUpdateFormState[key] = undefined)
    if (!Number.isNaN(props.updateId)) {
      const [r, e] = await fetchRoleInfoApi({id: props.updateId})
      if (e) {
        return message.warn(e.msg)
      }
      Object.keys(r).forEach(key => addOrUpdateFormState[key] = r[key])
    }
  }
}, {immediate: true});

</script>

<style scoped>

</style>
