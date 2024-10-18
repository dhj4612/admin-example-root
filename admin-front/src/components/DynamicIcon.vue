<template>
  <component :is="icon" v-if="icon"/>
</template>

<script setup>
import {computed} from 'vue';
import * as AntIcons from '@ant-design/icons-vue';

const props = defineProps({
  type: {
    type: String,
    required: true
  }
});

const icon = computed(() => AntIcons[props.type] || null);
</script>
