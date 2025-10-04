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
      
      

    </div>
    <q-toolbar-title class="q-ml-md">{{username}}</q-toolbar-title>
  </q-toolbar>
</template>


<script setup lang="ts">
   import { QBtn, QToolbar, QToolbarTitle } from 'quasar';
   import { defineProps } from 'vue';

   const props = defineProps({
       username: { type: String, default: '' }
   });
</script>
