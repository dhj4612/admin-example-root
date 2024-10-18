<script setup>
import {useRouter} from "vue-router";

const router = useRouter();
console.log(router.currentRoute);
</script>

<template>
  <h1 v-auth="'sys:menu:update'">Menu Index</h1>
</template>

<style scoped>

</style>
