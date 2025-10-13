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

import { ref, computed } from 'vue'
import axios from 'axios'

const allenatori = ref<any[]>([])
const anni = ref<any[]>([])
const squadre = ref<any[]>([])
const showForm = ref(false)

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
    const res = await axios.get('http://localhost:8080/allenatori/list')
    allenatori.value = res.data
  } catch (error) {
    allenatori.value = []
    console.error('Errore caricamento allenatori:', error)
  }
}

async function loadAnni() {
  try {
    const res = await axios.get('http://localhost:8080/anno/list')
    anni.value = res.data
  } catch (error) {
    anni.value = []
    console.error('Errore caricamento anni agonistici:', error)
  }
}

async function loadSquadre() {
  try {
    const res = await axios.get('http://localhost:8080/squadra/listaSquadre')
    squadre.value = res.data // DTO già con: id, nomeSquadra, nomeAllenatore, annoAgonistico
  } catch (error) {
    squadre.value = []
    console.error('Errore caricamento squadre:', error)
  }
}

async function onInsertSquadra(payload: any) {
  try {
    if (!payload.nome || !payload.idAllenatore || !payload.idAnnoAgonistico) {
      msgSquadra.value = 'Compila tutti i campi'
      return
    }
    await axios.post('http://localhost:8080/squadra/insert', {
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
      await axios.delete(`http://localhost:8080/squadra/delete/${row.id}`)
      await loadSquadre()
    } catch (error:any) {
      console.error('Errore cancellazione squadra:', error)
      alert(error.response?.data || 'Errore nella cancellazione')
    }
  }
}

function onAssociaAtleti(row: any) {
  // Esempio: naviga a una pagina dedicata
  // Se usi Vue Router:
  // router.push({ name: 'AssociaAtleti', params: { squadraId: row.id } })

  // Oppure apri una dialog/modal:
  alert(`Associa atleti alla squadra: ${row.nomeSquadra || row.nome} (ID: ${row.id})`)
}

// Init
loadAllenatori()
loadAnni()
loadSquadre()
</script>