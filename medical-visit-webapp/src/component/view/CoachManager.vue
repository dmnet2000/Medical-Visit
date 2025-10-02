<template>
  <q-card class="q-pa-lg">
    <q-card-section>
      <div class="text-h6 text-primary">Gestione Allenatori</div>
      <div class="row q-gutter-md q-mb-md">
        <q-btn
          color="primary"
          label="Lista Allenatori"
          @click="view = 'list'"
          :outline="view !== 'list'"
        />
        <q-btn
          color="primary"
          label="Ricerca"
          @click="view = 'search'"
          :outline="view !== 'search'"
        />
        <q-btn
          color="primary"
          label="Inserimento"
          @click="view = 'add'"
          :outline="view !== 'add'"
        />
      </div>
      <div v-if="view === 'list'">
       <q-table
          :rows="coaches"
          :columns="columns"
          row-key="id"
          class="q-mt-md"
          flat
          dense
        >
        <template v-slot:body-cell-azioni="props">
          <q-btn
            flat
            round
            dense
            icon="edit"
            color="primary"
            @click="onEditCoach(props.row)"
            class="q-mr-sm"
          />
          <q-btn
            flat
            round
            dense
            icon="delete"
            color="negative"
            @click="onDeleteCoach(props.row)"
          />
        </template>
      </q-table>
        <div v-if="coaches.length === 0" class="q-mt-md text-grey">
          Nessun allenatore presente.
        </div>
      </div>
      <div v-else-if="view === 'search'">
        <!-- Qui va il form di ricerca -->
        <div class="q-mt-md">[Form Ricerca Allenatore]</div>
      </div>
      <div v-else-if="view === 'add'">
        <q-form @submit.prevent="onCoachSave" class="q-mt-md">
        <q-input
            filled
            v-model="coach.nome"
            label="Nome"
            class="q-mb-sm"
            :rules="[val => !!val || 'Campo obbligatorio']"/>
        <q-input
            filled
            v-model="coach.cognome"
            label="Cognome"
            class="q-mb-sm"
            :rules="[val => !!val || 'Campo obbligatorio']" />
        <q-input
            filled
            v-model="coach.indirizzo"
            label="Indirizzo"
            class="q-mb-sm"
        />
        <q-input
            filled
            v-model="coach.indirizzoMail"
            label="Email"
            type="email"
            class="q-mb-sm"
            :rules="[
            val => !!val || 'Campo obbligatorio',
            val => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(val) || 'Email non valida'
            ]"
        />
        <q-input
            filled
            v-model="coach.numeroTelefono"
            label="Telefono"
            class="q-mb-sm"
        />
        <q-input
        filled
        v-model="coach.codiceFiscale"
        label="Codice Fiscale"
        class="q-mb-sm"
        :rules="[
            val => !!val || 'Campo obbligatorio',
            val => /^[A-Z0-9]{16}$/i.test(val) || 'Codice fiscale non valido'
        ]"
        />
        <div class="row justify-end">
            <q-btn
            color="secondary"
            type="submit"
            :label="isEditMode ? 'Salva Modifiche' : 'Salva Allenatore'"
            :disable="!isFormValid"
            />
        </div>
        </q-form>
      </div>
    </q-card-section>
  </q-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { computed } from 'vue'

const isFormValid = computed(() =>
  coach.value.nome &&
  coach.value.cognome &&
  coach.value.codiceFiscale &&
  coach.value.indirizzoMail &&
  /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(coach.value.indirizzoMail)
)

const view = ref<'list' | 'search' | 'add'>('list')

const coach = ref({
  nome: '',
  cognome: '',
  indirizzo: '',
  indirizzoMail: '',
  numeroTelefono: '',
  codiceFiscale: ''
})


const coaches = ref<any[]>([])
const columns = [
  { name: 'nome', label: 'Nome', field: 'nome', align: 'left', sortable: true },
  { name: 'cognome', label: 'Cognome', field: 'cognome', align: 'left', sortable: true },
  { name: 'indirizzo', label: 'Indirizzo', field: 'indirizzo', align: 'left' },
  { name: 'indirizzoMail', label: 'Email', field: 'indirizzoMail', align: 'left' },
  { name: 'numeroTelefono', label: 'Telefono', field: 'numeroTelefono', align: 'left' },
  { name: 'codiceFiscale', label: 'Codice Fiscale', field: 'codiceFiscale', align: 'left' },
  { name: 'azioni', label: 'Azioni', field: 'azioni', align: 'right' }
]

const isEditMode = ref(false)

async function loadCoaches() {
  try {
    const res = await axios.get('http://localhost:8080/allenatori/list')
    coaches.value = res.data
  } catch (error) {
    coaches.value = []
    console.error('Errore caricamento allenatori:', error)
  }
}

function showList() {
  view.value = 'list'
  loadCoaches()
}


async function onCoachSave() {
   try {
    if (isEditMode.value) {
      await axios.put(`http://localhost:8080/allenatori/update/${coach.value.id}`, coach.value)
      alert('Allenatore modificato con successo!')
    } else {
      await axios.post('http://localhost:8080/allenatori/insert', coach.value)
      alert('Allenatore salvato con successo!')
    }
    coach.value = {
      nome: '',
      cognome: '',
      indirizzo: '',
      indirizzoMail: '',
      numeroTelefono: '',
      codiceFiscale: ''
    }
    isEditMode.value = false
    showList()
  } catch (error) {
    alert('Errore nel salvataggio allenatore')
    console.error(error)
  }
}

// Carica la lista all'avvio se serve
if (view.value === 'list') {
  loadCoaches()
}

function onEditCoach(coachRow: any) {
  coach.value = { ...coachRow }
  isEditMode.value = true
  view.value = 'add'
}

async function onDeleteCoach(coachRow: any) {
  if (confirm(`Vuoi davvero cancellare l'allenatore ${coachRow.nome} ${coachRow.cognome}?`)) {
    try {
      await axios.delete(`http://localhost:8080/allenatori/delete/${coachRow.id}`)
      alert('Allenatore cancellato!')
      loadCoaches()
    } catch (error) {
      alert('Errore nella cancellazione')
      console.error(error)
    }
  }
}
</script>