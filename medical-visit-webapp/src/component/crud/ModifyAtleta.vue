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
import axios from 'axios';
import { nextTick } from 'vue'
import { useRouter, useRoute } from 'vue-router'
const router = useRouter()
const route = useRoute()
const msg = ref(route.query.msg || '')

const props = defineProps<{ atleta: any, show: boolean }>()
const emit = defineEmits(['update:show', 'saved'])
const data = ref(props.atleta?.dataScadenzaVisitaMedica || '')
const atletaStore = useAtletaStore()
const atleta = atletaStore.selected

watch(() => props.atleta, (val) => {
  data.value = val?.dataScadenzaVisitaMedica || ''
})

function salva() {
  const payload = {
    codFiscale: atleta?.codiceFiscale,
    newDate: data.value
  }
  axios.post('http://localhost:8080/atleti/update-visita', payload)
    .then(() => {
      emit('saved', data.value)
      emit('update:show', false)
      nextTick(() => {
        router.push({ path: '/home/search', query: { msg: 'Data aggiornata con successo!' } })
      })
    })
    .catch(err => {
      console.error('Errore nel salvataggio:', err)
      // Puoi mostrare un messaggio di errore qui
    })
  console.log('Salva nuova data:', data.value)
  emit('saved', data.value)
  emit('update:show', false)
}

function onUpdateShow(val: boolean) {
  emit('update:show', val)
}
</script>