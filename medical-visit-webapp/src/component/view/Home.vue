<template>
  <div>
    <div>
      <AppMenu 
        :username="authStore.user?.username ?? ''" 
        :tipoUtente="authStore.user?.tipoUtente ?? ''"
      />
    </div>
    
    <div v-if="isHomeRoot" class="home-container">
      <h1>Benvenuto nella Home di Visital Medic!</h1>
      <div class="user-info-box">
        <h3>Informazioni Utente</h3>
        <p><strong>Username:</strong> {{ authStore.user?.username }}</p>
        <p><strong>Tipo Utente:</strong> {{ authStore.user?.tipoUtente }}</p>
        <p><strong>Ruoli:</strong> {{ authStore.user?.roles?.join(', ') }}</p>
        <p v-if="authStore.user?.userId">
          <strong>ID Utente:</strong> {{ authStore.user?.userId }}
        </p>
      </div>
    </div>
    <div class="search-section">
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
import AppMenu from '../menu/Menu.vue'
import { useAuthStore } from '../../storage/auth'
import { useRouter } from 'vue-router'
import { computed } from 'vue'

const authStore = useAuthStore()
const router = useRouter()
const isHomeRoot = computed(() => router.currentRoute.value.path === '/home')
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

h1 {
  font-size: 2rem;
  margin-bottom: 20px;
}

.user-info-box {
  margin-top: 20px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  text-align: left;
}

.user-info-box h3 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #0a243b;
  border-bottom: 2px solid #007bff;
  padding-bottom: 10px;
}

.user-info-box p {
  margin: 10px 0;
  color: #333;
}

.user-info-box strong {
  color: #0a243b;
}
</style>