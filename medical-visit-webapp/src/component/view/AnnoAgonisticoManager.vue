<template>
  <q-card class="q-pa-lg">
    <q-card-section>
      <div class="text-h6 text-primary">Inserisci Anno Agonistico</div>
      <q-form @submit.prevent="onInsertAnno" class="q-mt-md">
        <q-input
          filled
          v-model="anno.anno"
          label="Anno Agonistico"
          class="q-mb-sm"
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <div class="row justify-end">
          <q-btn color="primary" type="submit" label="Salva" />
        </div>
      </q-form>
      <div v-if="msg" class="q-mt-md text-positive">{{ msg }}</div>
    </q-card-section>
  </q-card>
  <q-table
    :rows="anni"
    :columns="columns"
    row-key="id"
    class="q-mt-lg"
    flat
    dense
    />
    <div v-if="anni.length === 0" class="q-mt-md text-grey">
    Nessun anno agonistico presente.
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'

const anni = ref<any[]>([])
const columns = [
  { name: 'anno', label: 'Anno Agonistico', field: 'anno', align: 'left', sortable: true }
]

const anno = ref({ anno: '' })
const msg = ref('')

async function onInsertAnno() {
  try {
    await axios.post('http://localhost:8080/anno/insert', anno.value)
    msg.value = 'Anno agonistico inserito con successo!'
    anno.value.anno = ''
  } catch (error) {
    msg.value = 'Errore nell\'inserimento'
    console.error(error)
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


// Carica la lista all'avvio
loadAnni()

</script>