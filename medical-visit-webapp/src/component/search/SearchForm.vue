<template>
  <div>
    <q-form>
    <q-card class="q-pa-lg my-search-form">
      <q-card-section>
        <div class="text-h6 text-primary q-mb-md">Ricerca Utente</div>
        <q-form @submit.prevent="onSearch">
          <div class="row q-col-gutter-md q-mb-md">
            <div class="col">
              <q-input filled v-model="form.nome" label="Nome" prepend-icon="person" />
            </div>
            <div class="col">
              <q-input filled v-model="form.cognome" label="Cognome" prepend-icon="person_outline" />
            </div>
          </div>
          <div class="row q-col-gutter-md q-mb-md">
            <div class="col">
                <q-input filled v-model="form.codiceFiscale" label="Codice Fiscale" prepend-icon="badge" class="q-mb-md" />
            </div>
            <div class="col">
                <q-input filled v-model="form.dataNascita" label="Data di nascita" type="date" prepend-icon="event" class="q-mb-md" />      
            </div>
          </div>
          
          <div class="row justify-end">
            <q-btn color="primary" type="submit" >
              Cerca
            </q-btn>
          </div>
        </q-form>
      </q-card-section>
    </q-card>
</q-form>
</div>
</template>

<script setup lang="ts">
import { QForm, QInput, QBtn, QIcon } from 'quasar';
import axios from 'axios';
import { ref } from 'vue';

const form = ref({
  nome: '',
  cognome: '',
  codiceFiscale: '',
  dataNascita: ''
});

async function onSearch() {
    try{
        const response = await axios.post('URL_DEL_TUO_SERVIZIO', form.value);
        console.log('res:', response.data)
    }catch (error){
        console.error('Errore nella ricerca:', error);
    }
  
}
</script>

<style scoped>
.v-form {
  max-width: 400px;
  margin: 0 auto;
  padding: 24px;
  background: #f8f8f8;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(10,36,59,0.08);
}
</style>
