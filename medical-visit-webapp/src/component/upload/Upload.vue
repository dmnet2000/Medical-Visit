<template>
  <div>
    <input type="file" @change="onFileChange" />
    <button @click="uploadFile" :disabled="!selectedFile">Upload</button>
    <div v-if="uploadStatus">{{ uploadStatus }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const selectedFile = ref<File | null>(null)
const uploadStatus = ref<string>('')

function onFileChange(event: Event) {
  const files = (event.target as HTMLInputElement).files
  if (files && files.length > 0) {
    selectedFile.value = files[0]
    uploadStatus.value = ''
  }
}

async function uploadFile() {
  if (!selectedFile.value) return
  const formData = new FormData()
  formData.append('file', selectedFile.value)

  try {
    // Sostituisci l’URL con quello del tuo servizio REST
    const response = await fetch('https://tuo-backend/api/upload', {
      method: 'POST',
      body: formData,
      // Se serve autenticazione, aggiungi headers qui
    })
    if (response.ok) {
      uploadStatus.value = 'File caricato con successo!'
    } else {
      uploadStatus.value = 'Errore nel caricamento.'
    }
  } catch (error) {
    uploadStatus.value = 'Errore di rete.'
  }
}
</script>