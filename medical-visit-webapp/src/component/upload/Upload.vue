<template>
  <div>
    <input type="file" @change="onFileChange" />
    <button @click="uploadFile" :disabled="!selectedFile">Upload</button>
    <div v-if="uploadStatus">{{ uploadStatus }}</div>
    <div v-if="atleti.length">
      <h3>Atleti importati:</h3>
      <table>
        <thead>
          <tr>
            <th
              v-for="col in columns"
              :key="col"
              @click="sortBy(col)"
              style="cursor:pointer"
            >
              {{ col }}
              <span v-if="sortColumn === col">
                {{ sortAsc ? '▲' : '▼' }}
              </span>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="atleta in sortedAtleti" :key="atleta.id">
            <td v-for="col in columns" :key="col">{{ atleta[col] }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'

const selectedFile = ref<File | null>(null)
const uploadStatus = ref<string>('')
const atleti = ref<any[]>([])
const columns = ref<string[]>([])
const sortColumn = ref<string>('')
const sortAsc = ref<boolean>(true)

function onFileChange(event: Event) {
  const files = (event.target as HTMLInputElement).files
  if (files && files.length > 0) {
    selectedFile.value = files[0]
    uploadStatus.value = ''
    atleti.value = []
    columns.value = []
    sortColumn.value = ''
  }
}

async function uploadFile() {
  if (!selectedFile.value) return
  const formData = new FormData()
  formData.append('file', selectedFile.value)

  try {
    const response = await fetch('http://localhost:8080/atleti/import-excel', {
      method: 'POST',
      body: formData,
    })
    if (response.ok) {
      const data = await response.json()
      atleti.value = Array.isArray(data) ? data : []
      columns.value = atleti.value.length ? Object.keys(atleti.value[0]) : []
      uploadStatus.value = 'File caricato con successo!'
    } else {
      uploadStatus.value = 'Errore nel caricamento.'
      atleti.value = []
      columns.value = []
    }
  } catch (error) {
    uploadStatus.value = 'Errore di rete.'
    atleti.value = []
    columns.value = []
  }
}

function sortBy(col: string) {
  if (sortColumn.value === col) {
    sortAsc.value = !sortAsc.value
  } else {
    sortColumn.value = col
    sortAsc.value = true
  }
}

const sortedAtleti = computed(() => {
  if (!sortColumn.value) return atleti.value
  return [...atleti.value].sort((a, b) => {
    if (a[sortColumn.value] == null) return 1
    if (b[sortColumn.value] == null) return -1
    if (typeof a[sortColumn.value] === 'number' && typeof b[sortColumn.value] === 'number') {
      return sortAsc.value
        ? a[sortColumn.value] - b[sortColumn.value]
        : b[sortColumn.value] - a[sortColumn.value]
    }
    return sortAsc.value
      ? String(a[sortColumn.value]).localeCompare(String(b[sortColumn.value]))
      : String(b[sortColumn.value]).localeCompare(String(a[sortColumn.value]))
  })
})
</script>