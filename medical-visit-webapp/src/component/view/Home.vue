<template>
  <div>
    <div>
      <AppMenu :username="authStore.user ?? undefined"/>
    </div>
    
    <div class="home-container">
      <h1>Benvenuto nella Home di Visital Medic!</h1>
      <p>Login effettuato con successo.</p>
    </div>
    <div class="search-section">
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
import AppMenu from '../menu/Menu.vue';
import { useAuthStore } from '../../storage/auth';
import { useRouter } from 'vue-router';

  const authStore = useAuthStore();
  const router = useRouter();

  if (!authStore.isAuthenticated) {
    router.replace('/login');
}
</script>


<style scoped>
.home-container {
  max-width: 600px;
  margin: 50px auto;
  padding: 30px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background: #f8f8f8;
  text-align: center;
}

h1{
  font-size: 2rem;
}
</style>