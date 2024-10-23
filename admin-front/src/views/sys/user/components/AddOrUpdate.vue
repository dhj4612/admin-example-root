<template>
  <a-modal :open="props.open"
           :title="updateId ? '修改' : '新增'"
           :width="800"
           centered
           @cancel="onModelClose"
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
        <a-form-item required label="用户名" name="username">
          <a-input v-model:value.trim="addOrUpdateFormState.username"/>
        </a-form-item>

        <a-form-item required label="手机号" name="mobile">
          <a-input v-model:value.trim="addOrUpdateFormState.mobile"/>
        </a-form-item>

        <a-form-item :required="!updateId" label="密码" name="password">
          <a-input-password v-model:value.trim="addOrUpdateFormState.password"/>
        </a-form-item>

        <a-form-item required label="姓名" name="realName">
          <a-input v-model:value.trim="addOrUpdateFormState.realName"/>
        </a-form-item>

        <a-form-item label="性别" name="gender">
          <a-radio-group v-model:value.trim="addOrUpdateFormState.gender">
            <a-radio v-for="item in Gender.excludeCodes(2)" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item required label="状态" name="status">
          <a-radio-group v-model:value="addOrUpdateFormState.status">
            <a-radio v-for="item in Status.toList()" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item label="角色" name="roleIds">
          <a-select
              v-model:value="addOrUpdateFormState.roleIds"
              mode="multiple"
              style="width: 100%"
              placeholder="Please select">
            <a-select-option v-for="item in roleSelectorData"
                             :value="item.id">
              {{ item.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </div>
  </a-modal>
</template>

<script setup>
import {reactive, ref, watch} from "vue";
import {Gender, Status} from "@/utils/constant/UserConst.js";
import {fetchRoleListApi, fetchUserUpdateInfoApi, userAddOrUpdateApi} from "@/api/index.js";
import {message} from "ant-design-vue";

const props = defineProps({
  open: {type: Boolean, default: false},
  updateId: {type: Number, default: NaN}
})
const emits = defineEmits(['onModelOk', 'onModelClose', 'onRefresh']);

const modelConfirmLoading = ref(false)
const addOrUpdateFormState = reactive({})
const addOrUpdateFormRef = ref()
const roleSelectorData = ref([])

const onModelClose = _ => emits('onModelClose')

const onModelConfirm = _ => {
  addOrUpdateFormRef.value.validate()
      .then(async formState => {
        modelConfirmLoading.value = true
        if (!Number.isNaN(props.updateId)) {
          formState.id = props.updateId
        }
        if (!formState.menuIds) {
          formState.menuIds = []
        }
        console.log(formState)
        const [_, e] = await userAddOrUpdateApi(formState)
        if (e) {
          modelConfirmLoading.value = false
          return message.warn(e.msg)
        }
        modelConfirmLoading.value = false
        emits('onModelClose')
        emits('onRefresh')
      }).catch(_ => {
  })
}

watch(_ => props.open, async _ => {
  if (props.open === true) {
    Object.keys(addOrUpdateFormState).forEach(key => addOrUpdateFormState[key] = undefined)
    const [r, e] = await fetchRoleListApi()
    if (e) {
      return message.warn(e.msg)
    }
    roleSelectorData.value = r

    if (!Number.isNaN(props.updateId)) {
      const [r, e] = await fetchUserUpdateInfoApi({id: props.updateId})
      if (e) {
        return message.warn(e.msg)
      }
      Object.keys(r).forEach(key => addOrUpdateFormState[key] = r[key])
    }
  }
})
</script>

<style scoped>

</style>
