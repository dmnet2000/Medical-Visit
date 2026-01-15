<template>
    <div class="register-page">
        <div class="logo-container">
            <img src="../../../public/Logo.png" alt="Volley Mogliano"  />
        </div>

        <div class="register-container">
        <h2>Registrazione</h2>
        <form @submit.prevent="handleRegister">
          <label>
            Username:
            <input v-model="username" type="text" required :disabled="loading" />
          </label>
          <label>
            Password:
            <input v-model="password" type="password" required :disabled="loading" />
          </label>
          <label>
            Conferma Password:
            <input v-model="confirmPassword" type="password" required :disabled="loading" />
          </label>
          <label>
            Tipo Utente:
            <select v-model="tipoUtente" required :disabled="loading">
              <option value="">Seleziona...</option>
              <option value="ATLETA">Atleta</option>
              <option value="ALLENATORE">Allenatore</option>
              <option value="ADMIN">Admin</option>
            </select>
          </label>
          <label>
            ID Utente (opzionale):
            <input v-model.number="idUser" type="number" :disabled="loading" />
          </label>
          <button type="submit" :disabled="loading">
            {{ loading ? 'Registrazione in corso...' : 'Registrati' }}
          </button>
          <div v-if="error" class="error">{{ error }}</div>
          <div v-if="success" class="success">{{ success }}</div>
          <div class="login-link">
            Hai già un account? 
            <router-link to="/login">Accedi qui</router-link>
          </div>
        </form>
      </div>
    </div>
</template>


<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/storage/auth'
import { register as registerService } from '@/services/authService'
import type { AuthUser } from '@/storage/auth'

const router = useRouter()
const authStore = useAuthStore()

const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const tipoUtente = ref('')
const idUser = ref<number | null>(null)
const error = ref('')
const success = ref('')
const loading = ref(false)

async function handleRegister() {
  error.value = ''
  success.value = ''

  // Validazione password
  if (password.value.length < 8) {
    error.value = 'La password deve contenere almeno 8 caratteri'
    return
  }

  if (password.value !== confirmPassword.value) {
    error.value = 'Le password non corrispondono'
    return
  }

  if (!tipoUtente.value) {
    error.value = 'Seleziona un tipo utente'
    return
  }

  loading.value = true

  try {
    const response = await registerService({
      username: username.value,
      password: password.value,
      tipoUtente: tipoUtente.value as 'ATLETA' | 'ALLENATORE' | 'ADMIN',
      idUser: idUser.value,
    })

    success.value = 'Registrazione completata! Login automatico in corso...'

    // Auto-login dopo registrazione
    const authUser: AuthUser = {
      username: response.username,
      token: response.token,
      tipoUtente: response.tipoUtente,
      roles: response.roles,
      userId: response.userId,
    }

    authStore.login(authUser)

    // Redirect dopo un breve delay
    setTimeout(() => {
      router.push('/home')
    }, 1000)
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore durante la registrazione. Riprova.'
  } finally {
    loading.value = false
  }
}
</script>


<style scoped>
.register-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.register-container {
  max-width: 400px;
  margin: 30px auto;
  padding: 32px 24px;
  border-radius: 16px;
  background: linear-gradient(135deg, #ffffff 70%, #e3eafc 100%);
  box-shadow: 0 8px 32px rgba(10,36,59,0.15), 0 1.5px 4px rgba(10,36,59,0.10);
  border: none;
  transition: box-shadow 0.2s;
}

.register-container:hover {
  box-shadow: 0 12px 40px rgba(10,36,59,0.22), 0 2px 8px rgba(10,36,59,0.13);
}

.register-container h2 {
  text-align: center;
  color: #0a243b;
  margin-bottom: 18px;
  font-weight: 700;
  letter-spacing: 1px;
}

label {
  display: block;
  margin-bottom: 14px;
  color: #0a243b;
  font-weight: 500;
}

input, select {
  width: 100%;
  padding: 10px;
  margin-top: 4px;
  border: 1px solid #b0c4de;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.2s, box-shadow 0.2s;
  outline: none;
  background: #f7faff;
}

input:disabled, select:disabled {
  background: #e8eef5;
  cursor: not-allowed;
  opacity: 0.7;
}

input:focus:not(:disabled), select:focus:not(:disabled) {
  border-color: #007bff;
  box-shadow: 0 0 0 2px #cce4ff;
}

button {
  width: 100%;
  padding: 10px;
  margin-top: 18px;
  background: linear-gradient(90deg, #007bff 60%, #0a243b 100%);
  color: #fff;
  border: none;
  border-radius: 6px;
  font-size: 17px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s, box-shadow 0.2s;
  box-shadow: 0 2px 8px rgba(10,36,59,0.08);
}

button:hover:not(:disabled) {
  background: linear-gradient(90deg, #0056b3 60%, #0a243b 100%);
}

button:disabled {
  background: #cccccc;
  cursor: not-allowed;
  opacity: 0.6;
}

.error {
  color: #d32f2f;
  margin-top: 12px;
  text-align: center;
  font-weight: 500;
  background: #ffeaea;
  border-radius: 4px;
  padding: 6px 0;
}

.success {
  color: #2e7d32;
  margin-top: 12px;
  text-align: center;
  font-weight: 500;
  background: #e8f5e9;
  border-radius: 4px;
  padding: 6px 0;
}

.login-link {
  text-align: center;
  margin-top: 16px;
  color: #0a243b;
  font-size: 14px;
}

.login-link a {
  color: #007bff;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.2s;
}

.login-link a:hover {
  color: #0056b3;
  text-decoration: underline;
}

.logo-container {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

img {
  max-width: 100%;
  width: 220px;
  height: auto;
  display: block;
}
</style>