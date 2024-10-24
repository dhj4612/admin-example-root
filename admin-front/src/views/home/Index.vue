<template>
  <div style="display: flex;justify-content: center;align-items: center;height: 100%">
    <h1>{{ currentTime }}</h1>
  </div>
</template>

<script setup>
import {computed, onUnmounted, ref} from "vue";
import {yyyy_mm_dd_hh_mm_ss} from "@/utils/tools.js";

const timestamp = ref(Date.now());
const currentTime = computed(() => yyyy_mm_dd_hh_mm_ss(timestamp.value))

const intervalId = setInterval(_ => {
  timestamp.value = Date.now()
}, 1000)

onUnmounted(_ => clearInterval(intervalId))

</script>


<style scoped>

</style>
