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
            <input v-model="username" type="text" required />
          </label>
          <label>
            Password:
            <input v-model="password" type="password" required />
          </label>
          <button type="submit">Registrati</button>
          <div v-if="error" class="error">{{ error }}</div>
          <div class="login-link">
            Hai già un account? 
            <router-link to="/login">Accedi qui</router-link>
          </div>
        </form>
      </div>
    </div>
</template>


<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
// import { register as registerService } from '@/services/authService';

const router = useRouter();

const form = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
});

const error = ref('');
const success = ref('');

async function handleRegister() {
  error.value = '';
  success.value = '';

  // Validazione password
  if (form.value.password !== form.value.confirmPassword) {
    error.value = 'Le password non corrispondono';
    return;
  }

  try {
    // await registerService(form.value.username, form.value.email, form.value.password);
    
    // Temporaneo per test
    success.value = 'Registrazione completata! Reindirizzamento al login...';
    setTimeout(() => {
      router.push('/login');
    }, 2000);
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore durante la registrazione';
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

input {
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

input:focus {
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

button:hover {
  background: linear-gradient(90deg, #0056b3 60%, #0a243b 100%);
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