<template>
  <q-card class="q-pa-lg q-mt-lg">
    <q-card-section>
      <div class="text-h6 text-primary">Inserisci Squadra</div>
      <q-form v-if="allenatori.length && anni.length" @submit.prevent="onInsertSquadra" class="q-mt-md">
        <q-select
          filled
          v-model="squadra.idAllenatore"
          :options="allenatori.map(a => ({ label: a.nome + ' ' + a.cognome, value: a.id }))"
          label="Allenatore"
          class="q-mb-sm"
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <q-select
          filled
          v-model="squadra.idAnnoAgonistico"
          :options="anni.map(a => ({ label: a.anno, value: a.id }))"
          label="Anno Agonistico"
          class="q-mb-sm"
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
             <q-input
          filled
            v-model="squadra.nome"
          label="Nome Squadra"
          class="q-mb-sm"
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <div class="row justify-end">
          <q-btn color="primary" type="submit" label="Salva Squadra" />
        </div>
      </q-form>
      <div v-if="msgSquadra" class="q-mt-md text-positive">{{ msgSquadra }}</div>
    </q-card-section>
  </q-card>
  <div>{{ squadra }}</div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'

const allenatori = ref<any[]>([])
const anni = ref<any[]>([])

const squadra = ref({
  nome: '',
  idAllenatore: null,
  idAnnoAgonistico: null
})
const msgSquadra = ref('')

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

async function onInsertSquadra() {
  try {
    if (!squadra.value.idAllenatore || !squadra.value.idAnnoAgonistico) {
        alert('Seleziona allenatore e anno agonistico');
        return;
    }
    console.log('Allenatori options:', allenatori.value.map(a => ({ label: a.nome + ' ' + a.cognome, value: a.id })))
    console.log('Anni options:', anni.value.map(a => ({ label: a.anno, value: a.id })))
    squadra.value.idAllenatore = Number(squadra.value.idAllenatore.value)
    squadra.value.idAnnoAgonistico = Number(squadra.value.idAnnoAgonistico.value)
    console.log('Squadra to insert:', squadra.value)
    await axios.post('http://localhost:8080/squadra/insert', squadra.value)
    msgSquadra.value = 'Squadra inserita con successo!'
    squadra.value.idAllenatore = null
    squadra.value.idAnnoAgonistico = null
  } catch (error) {
    msgSquadra.value = 'Errore nell\'inserimento squadra'
    console.error(error)
  }
}

// Carica dati all'avvio
loadAllenatori()
loadAnni()
</script>