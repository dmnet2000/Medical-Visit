<template>
  <q-table
    :rows="atleti"
    :columns="columns"
    row-key="codiceFiscale"
    flat
    dense
  >
    <template v-slot:body-cell-modifica="props">
      <q-btn
        flat
        round
        dense
        icon="edit"
        color="primary"
        @click.stop="onModifica(props.row)"
      />
    </template>
  </q-table>
</template>


<script setup lang="ts">
import { defineProps, defineEmits } from 'vue'

const props = defineProps<{ atleti: any[], columns: any[] }>()
const emit = defineEmits(['modifica'])

function onModifica(row: any) {
  emit('modifica', row)
}
</script>