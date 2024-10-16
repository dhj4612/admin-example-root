<template>
  <a-config-provider :locale="zhCN">
    <RouterView/>
  </a-config-provider>
</template>
<script setup>
import zhCN from 'ant-design-vue/es/locale/zh_CN';
</script>

<style>

</style>
