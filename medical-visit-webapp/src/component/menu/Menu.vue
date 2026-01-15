<template>
  <q-toolbar class="bg-primary text-white">
    <div class="row justify-start q-gutter-xl">
      <div class="menu-btn">
        <q-btn flat round icon="home" @click="$router.push('/home')" />
        <div class="menu-label">Home</div>
      </div>
      <div class="menu-btn">
        <q-btn flat round icon="import_contacts" @click="$router.push('/home/import')" />
        <div class="menu-label">Importa...</div>
      </div>
      <div class="menu-btn">
        <q-btn flat round icon="search" @click="$router.push('/home/search')" />
        <div class="menu-label">Ricerca Atleta</div>
      </div>
      <div class="menu-btn">
        <q-btn flat round icon="search" @click="$router.push('/home/coach')" />
        <div class="menu-label">Gestione Allenatore</div>
      </div>
      <div class="menu-btn">
        <q-btn flat round icon="search" @click="$router.push('/home/anno')" />
        <div class="menu-label">Gestione Anno Agonistico</div>
      </div>
      <div class="menu-btn">
        <q-btn flat round icon="search" @click="$router.push('/home/squadre')" />
        <div class="menu-label">Gestione Squadre</div>
      </div>
    </div>
    <q-toolbar-title class="q-ml-md">{{ username }} ({{ tipoUtente }})</q-toolbar-title>
    <q-btn 
      flat 
      round 
      icon="logout" 
      @click="handleLogout"
    >
      <q-tooltip>Logout</q-tooltip>
    </q-btn>
  </q-toolbar>
</template>


<script setup lang="ts">
import { QBtn, QToolbar, QToolbarTitle, QTooltip } from 'quasar'
import { defineProps } from 'vue'
import { useAuthStore } from '@/storage/auth'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'

const props = defineProps({
  username: { type: String, default: '' },
  tipoUtente: { type: String, default: '' }
})

const authStore = useAuthStore()
const router = useRouter()
const $q = useQuasar()

function handleLogout() {
  $q.dialog({
    title: 'Conferma Logout',
    message: 'Sei sicuro di voler uscire?',
    cancel: {
      label: 'Annulla',
      flat: true
    },
    ok: {
      label: 'Logout',
      flat: true
    },
    persistent: false
  }).onOk(() => {
    authStore.logout()
    router.push('/login')
  })
}
</script>

