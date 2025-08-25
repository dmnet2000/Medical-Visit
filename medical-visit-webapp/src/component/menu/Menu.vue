<template>
    <q-toolbar class="bg-primary text-white">
        <div class="row justify-start q-gutter-xl">
            <q-btn flat round icon="home" @click="$router.push('/home')" />
            <!--<q-btn flat round icon="person" @click="$router.push('/profile')" />-->
            <q-btn flat round icon="find" @click="$router.push('/home/search')" />
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
