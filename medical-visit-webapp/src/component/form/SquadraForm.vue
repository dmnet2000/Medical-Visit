<template>
  <q-card class="q-pa-lg q-mt-lg">
    <q-card-section>
      <div class="text-h6 text-primary">Inserisci Squadra</div>
      <q-form
        v-if="allenatoriOptions.length && anniOptions.length"
        @submit.prevent="submit"
        class="q-mt-md"
      >
        <q-input
          filled
          v-model="localSquadra.nome"
          label="Nome Squadra"
          class="q-mb-sm"
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <q-select
          filled
          v-model="localSquadra.idAllenatore"
          :options="allenatoriOptions"
          label="Allenatore"
          class="q-mb-sm"
          emit-value
          map-options
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <q-select
          filled
          v-model="localSquadra.idAnnoAgonistico"
          :options="anniOptions"
          label="Anno Agonistico"
          class="q-mb-sm"
          emit-value
          map-options
          :rules="[val => !!val || 'Campo obbligatorio']"
        />
        <div class="row justify-end">
          <q-btn color="primary" type="submit" label="Salva Squadra" />
          <q-btn flat color="grey" label="Annulla" @click="$emit('cancel')" class="q-ml-sm" />
        </div>
      </q-form>
      <div v-else class="text-grey q-mt-md">
        Caricamento dati di riferimento...
      </div>
      <div v-if="msg" class="q-mt-md text-positive">{{ msg }}</div>
    </q-card-section>
  </q-card>
</template>

<script setup lang="ts">
import { ref, watch, toRefs } from 'vue'

const props = defineProps<{
  allenatoriOptions: Array<any>,
  anniOptions: Array<any>,
  squadra: { nome: string, idAllenatore: number | null, idAnnoAgonistico: number | null }
}>()
const emit = defineEmits(['submit', 'cancel'])

const localSquadra = ref({ ...props.squadra })
const msg = ref('')

watch(() => props.squadra, (val) => {
  localSquadra.value = { ...val }
})

function submit() {
  if (!localSquadra.value.nome || !localSquadra.value.idAllenatore || !localSquadra.value.idAnnoAgonistico) {
    msg.value = 'Compila tutti i campi'
    return
  }
  emit('submit', { ...localSquadra.value })
  msg.value = ''
}
</script>