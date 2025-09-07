<template>
  <div>
    <q-form>
    <q-card class="q-pa-lg my-search-form">
      <q-card-section>
        <div class="text-h6 text-primary q-mb-md">Ricerca Atleta</div>
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

    <!-- Risultati della ricerca -->
    <div v-if="atleti.length" class="q-mt-lg">
      <q-card>
        <q-card-section>
          <div class="text-h6">Risultati ricerca</div>
          <q-table
            :rows="atleti"
            :columns="columns"
            row-key="codiceFiscale" 
            flat
            dense
          />
        </q-card-section>
      </q-card>
    </div>

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
  
});
const atleti = ref<any[]>([]);
const columns = ref<any[]>([]);

async function onSearch() {
    try{
        const response = await axios.post('http://localhost:8080/atleti/search', form.value);
        console.log('res:', response.data)
        atleti.value = Array.isArray(response.data) ? response.data : [];
    // Genera le colonne dinamicamente dalla prima risposta
   
    columns.value = atleti.value.length    
      ? Object.keys(atleti.value[0]).map(key => ({
          name: key,
          label: key.charAt(0).toUpperCase() + key.slice(1),
          field: key,
          align: 'left',
          sortable: true
        }))
      : [];
       console.log('columns:', columns.value)
    }catch (error){
        console.error('Errore nella ricerca:', error);
        atleti.value = [];
        columns.value = [];
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
