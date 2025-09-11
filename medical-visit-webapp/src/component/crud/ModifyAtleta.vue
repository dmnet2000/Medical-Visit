<template>
  <q-dialog :model-value="show" @update:model-value="onUpdateShow">
    <q-card>
      <q-card-section>
        <div v-if="atleta" >
          <div><b>Nome:</b> {{ atleta?.nome }}</div>
          <div><b>Cognome:</b> {{ atleta?.cognome }}</div>
          <div><b>Codice Fiscale:</b> {{ atleta?.codiceFiscale }}</div>
          <div><b>Data Scadenza Attuale:</b> {{ atleta?.dataScadenzaVisitaMedica }}</div>
        </div>
        <q-input type="date" v-model="data" label="Nuova data certificato" />
      </q-card-section>
      <q-card-actions>
        <q-btn label="Salva" color="primary" @click="salva" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch } from 'vue'
import { useAtletaStore } from '@/storage/atleti';

const props = defineProps<{ atleta: any, show: boolean }>()
const emit = defineEmits(['update:show', 'saved'])
const data = ref(props.atleta?.dataScadenzaVisitaMedica || '')
const atletaStore = useAtletaStore()
const atleta = atletaStore.selected


watch(() => props.atleta, (val) => {
  data.value = val?.dataScadenzaVisitaMedica || ''
})

function salva() {
  console.log('Salva nuova data:', data.value)
  emit('saved', data.value)
  emit('update:show', false)
}

function onUpdateShow(val: boolean) {
  emit('update:show', val)
}
</script>