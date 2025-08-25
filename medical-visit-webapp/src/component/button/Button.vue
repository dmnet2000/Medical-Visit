<template>
  <v-btn :color="color" :variant="variant" block @click="onClick">
    <v-icon v-if="icon" left :icon="icon"></v-icon>
    {{ label }}
  </v-btn>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  label: { type: String, required: true },
  icon: { type: String, default: '' },
  color: { type: String, default: 'primary' },
  variant: { type: String, default: 'elevated' }
});

const emit = defineEmits(['click']);

function onClick() {
  emit('click');
}
</script>

<style scoped>
</style>