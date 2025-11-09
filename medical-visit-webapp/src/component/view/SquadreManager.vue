<template>
  <div>
    <q-btn
      color="primary"
      label="Nuova Squadra"
      @click="showForm = !showForm"
      class="q-mb-md"
    />  
  </div>
  <SquadraForm
    v-if="showForm"
    :allenatori-options="allenatoriOptions"
    :anni-options="anniOptions"
    :squadra="squadra"
    @submit="onInsertSquadra"
    @cancel="showForm = false"
  />
  <q-table
    :rows="squadre"
    :columns="columns"
    row-key="id"
    class="q-mt-lg"
    flat
    dense
  >
    <template #body-cell-azioni="props">
      <q-btn
        flat
        dense
        round
        icon="person_add"
        color="primary"
        @click="onAssociaAtleti(props.row)"
        class="q-mr-sm"
      />
      <q-btn
        flat
        dense
        round
        icon="delete"
        color="negative"
        @click="onDeleteSquadra(props.row)"
      />
    </template>
  </q-table>
  <div v-if="squadre.length === 0" class="q-mt-md text-grey">
    Nessuna squadra presente.
  </div>
</template>

<script setup lang="ts">
import SquadraForm from '../form/SquadraForm.vue'
import * as squadraService from '@/services/squadraService'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const allenatori = ref<any[]>([])
const anni = ref<any[]>([])
const squadre = ref<any[]>([])
const showForm = ref(false)
const loading = ref(false)

const columns = [
  { name: 'id', label: 'ID', field: 'id', align: 'left', sortable: true },
  { name: 'nomeSquadra', label: 'Nome Squadra', field: 'nomeSquadra', align: 'left', sortable: true },
  { name: 'nomeAllenatore', label: 'Allenatore', field: 'nomeAllenatore', align: 'left' },
  { name: 'annoAgonistico', label: 'Anno Agonistico', field: 'annoAgonistico', align: 'left' },
  { name: 'azioni', label: 'Azioni', field: 'azioni', align: 'right' }
]

const squadra = ref({
  nome: '',
  idAllenatore: null as number | null,
  idAnnoAgonistico: null as number | null
})

const msgSquadra = ref('')

const allenatoriOptions = computed(() =>
  allenatori.value.map(a => ({
    label: `${a.nome} ${a.cognome}`,
    value: a.id
  }))
)

const anniOptions = computed(() =>
  anni.value.map(a => ({
    label: a.anno,
    value: a.id
  }))
)

async function loadAllenatori() {
  try {
    allenatori.value = await squadraService.fetchAllenatori()
  } catch (error) {
    allenatori.value = []
    console.error('Errore caricamento allenatori:', error)
  }
}

async function loadAnni() {
  try {
    anni.value = await squadraService.fetchAnni()
  } catch (error) {
    anni.value = []
    console.error('Errore caricamento anni agonistici:', error)
  }
}

async function loadSquadre() {
  try {
    loading.value = true
   squadre.value = await squadraService.fetchSquadre()
  } catch (e) {
    squadre.value = []
    console.error('Errore caricamento squadre:', e)
  } finally {
    loading.value = false
  }
}

async function onInsertSquadra(payload: any) {
  try {
    if (!payload.nome || !payload.idAllenatore || !payload.idAnnoAgonistico) {
      msgSquadra.value = 'Compila tutti i campi'
      return
    }
    await squadraService.createSquadra({
      nome: payload.nome,
      idAllenatore: Number(payload.idAllenatore),
      idAnnoAgonistico: Number(payload.idAnnoAgonistico)
    })
    msgSquadra.value = 'Squadra inserita con successo!'
    squadra.value = { nome: '', idAllenatore: null, idAnnoAgonistico: null }
    showForm.value = false
    await loadSquadre()
  } catch (error) {
    msgSquadra.value = 'Errore nell\'inserimento squadra'
    console.error(error)
  }
}


async function onDeleteSquadra(row: any) {
  if (!row.id) {
    alert('ID squadra non disponibile')
    return
  }
  if (confirm(`Cancellare la squadra "${row.nomeSquadra || row.nome}"?`)) {
    try {
      await squadraService.deleteSquadra(row.id)
      await loadSquadre()
    } catch (error:any) {
      console.error('Errore cancellazione squadra:', error)
      alert(error.response?.data || 'Errore nella cancellazione')
    }
  }
}

function onAssociaAtleti(row: any) {
  alert(`Associa atleti alla squadra: ${row.nomeSquadra || row.nome} (ID: ${row.id})`)
  router.push({ name: 'AssociaAtleti', params: { id: String(row.id) } })
}

// Init
loadAllenatori()
loadAnni()
loadSquadre()
</script>