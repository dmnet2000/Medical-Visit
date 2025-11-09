<template>
  <q-page padding>
    <div class="row items-center justify-between q-mb-md">
      <div>
        <div class="text-h6">Associa Atleti a: {{ squadra?.nomeSquadra || squadra?.nome }}</div>
        <div class="text-subtitle2 q-mt-xs">{{ squadra?.nomeAllenatore }} — {{ squadra?.annoAgonistico }}</div>
      </div>
      <div>
        <q-btn flat icon="arrow_back" label="Indietro" @click="goBack" />
      </div>
    </div>

    <q-input outlined v-model="search" debounce="300" placeholder="Cerca atleti (nome o cognome)" class="q-mb-md" />

    <q-table
      :rows="filteredAtleti"
      :columns="columns"
      row-key="id"
      flat
      dense
    >
      <template #body-cell-selezione="props">
        <q-checkbox v-model="selectedMap[props.row.id]" @update:model-value="toggleSelection(props.row.id)" />
      </template>
    </q-table>

    <div class="row justify-end q-mt-md">
      <q-btn color="primary" label="Associa selezionati" @click="saveAssociations" :disable="!hasSelection" />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { ref, computed, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import * as squadraService from '@/services/squadraService'
import * as atletaService from '@/services/atletaService'
import { QBtn, QInput, QTable, QCheckbox } from 'quasar'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)

const squadra = ref<any>(null)
const atleti = ref<any[]>([])
const search = ref('')
const loading = ref(false)

const selectedMap = reactive<Record<number, boolean>>({})

const columns = [
  { name: 'selezione', label: '', field: 'selezione', align: 'center' },
  { name: 'nome', label: 'Nome', field: (r:any) => `${r.nome} ${r.cognome}`, align: 'left' },
  { name: 'codice', label: 'Codice', field: 'codiceFiscale', align: 'left' }
]

const filteredAtleti = computed(() => {
  const q = search.value.trim().toLowerCase()
  if (!q) return atleti.value
  return atleti.value.filter(a =>
    `${a.nome} ${a.cognome}`.toLowerCase().includes(q) ||
    (a.codiceFiscale || '').toLowerCase().includes(q)
  )
})

const hasSelection = computed(() => Object.values(selectedMap).some(v => v))

function toggleSelection(id:number) {
  selectedMap[id] = !selectedMap[id]
}

async function loadData() {
  loading.value = true
  try {
    squadra.value = await squadraService.fetchSquadraById(id)
    atleti.value = await atletaService.fetchAtleti()
    // se vuoi pre-selezionare atleti già associati, mappa qui:
    // esempio: squadra.value.atletiIds?.forEach((aid:number) => selectedMap[aid]=true)
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

async function saveAssociations() {
  const selectedIds = Object.entries(selectedMap)
    .filter(([,v]) => v)
    .map(([k]) => Number(k))
  if (!selectedIds.length) {
    alert('Seleziona almeno un atleta')
    return
  }
  try {
    await squadraService.associaAtleti(id, selectedIds)
    alert('Atleti associati con successo')
    router.back()
  } catch (e:any) {
    console.error(e)
    alert(e.response?.data || 'Errore durante l\'associazione')
  }
}

function goBack() {
  router.back()
}

onMounted(loadData)
</script>