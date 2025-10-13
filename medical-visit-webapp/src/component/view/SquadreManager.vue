<template>
  <q-card class="q-pa-lg q-mt-lg">
    <q-card-section>
      <div class="text-h6 text-primary">Inserisci Squadra</div>
      <q-form
        v-if="allenatori.length && anni.length"
        @submit.prevent="onInsertSquadra"
        class="q-mt-md"
      >
        <q-input
          filled
          v-model="squadra.nome"
          label="Nome Squadra"
          class="q-mb-sm"
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <q-select
          filled
          v-model="squadra.idAllenatore"
          :options="allenatoriOptions"
          label="Allenatore"
          class="q-mb-sm"
          emit-value
          map-options
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <q-select
          filled
          v-model="squadra.idAnnoAgonistico"
          :options="anniOptions"
          label="Anno Agonistico"
          class="q-mb-sm"
          emit-value
          map-options
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <div class="row justify-end">
          <q-btn color="primary" type="submit" label="Salva Squadra" />
        </div>
      </q-form>
      <div v-else class="text-grey q-mt-md">
        Caricamento dati di riferimento...
      </div>
      <div v-if="msgSquadra" class="q-mt-md text-positive">{{ msgSquadra }}</div>
    </q-card-section>
  </q-card>

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
import { ref, computed } from 'vue'
import axios from 'axios'

const allenatori = ref<any[]>([])
const anni = ref<any[]>([])
const squadre = ref<any[]>([])

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

async function onInsertSquadra() {
  try {
    if (!squadra.value.nome || !squadra.value.idAllenatore || !squadra.value.idAnnoAgonistico) {
      msgSquadra.value = 'Compila tutti i campi'
      return
    }
    const payload = {
      nome: squadra.value.nome,
      idAllenatore: Number(squadra.value.idAllenatore),
      idAnnoAgonistico: Number(squadra.value.idAnnoAgonistico)
    }
    await axios.post('http://localhost:8080/squadra/insert', payload)
    msgSquadra.value = 'Squadra inserita con successo!'
    squadra.value = { nome: '', idAllenatore: null, idAnnoAgonistico: null }
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

// Init
loadAllenatori()
loadAnni()
loadSquadre()
</script>