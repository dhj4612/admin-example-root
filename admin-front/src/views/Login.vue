<template>
  <div style="width: 100vw;height:100vh;display: flex;align-items: center;justify-content: center">
    <a-form
        :model="formState"
        name="basic"
        autocomplete="off"
        :label-col="{span: 7}"
        label-align="right"
        @finish="onFinish"
        @finishFailed="onFinishFailed">

      <a-form-item
          style="width: 500px"
          label="用户名"
          name="phone"
          :rules="[{ required: true, message: '请输入用户名' }]">
        <a-input allow-clear v-model:value="formState['phone']"/>
      </a-form-item>

      <a-form-item
          style="width: 500px;margin-top: 35px"
          label="密码"
          name="password"
          :rules="[{ required: true, message: '请输入密码' }]">
        <a-input-password allow-clear v-model:value="formState['password']"/>
      </a-form-item>

      <a-form-item :wrapper-col="{offset: 7}">
        <div style="width: 100px">
          <a-button block :loading="loading" type="primary" html-type="submit">登录</a-button>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>
<script setup>
import {reactive, ref} from 'vue';
import {loginApi} from "@/api/index.js";
import {hasAuthorization, setAuthorization, setRefreshAuthorization} from "@/utils/authorization.js";
import {useRouter} from "vue-router";
import {message} from 'ant-design-vue';

const router = useRouter()
const formState = reactive({});
const loading = ref(false)

const checkLogin = _ => {
  if (hasAuthorization()) {
    router.replace('/')
  }
}

const onFinish = async values => {
  loading.value = true
  const [res, err] = await loginApi(values)

  if (err) {
    message.warning(err?.msg || '未知错误')
    return loading.value = false
  }

  const {token, refreshToken} = res
  setAuthorization(token)
  setRefreshAuthorization(refreshToken)
  await router.replace('/')
  loading.value = false
};

const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};

checkLogin()
</script>

<style scoped>
</style>
