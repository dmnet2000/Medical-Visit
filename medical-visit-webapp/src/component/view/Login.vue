<template>
<div class="login-page">
    <div class="logo-container">
        <img src="../../../public/Logo.png" alt="Volley Mogliano"  />
    </div>

    <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <label>
        Username:
        <input v-model="username" type="text" required />
      </label>
      <label>
        Password:
        <input v-model="password" type="password" required />
      </label>
      <button type="submit">Accedi</button>
      <div v-if="error" class="error">{{ error }}</div>
      <div class="register-link">
        Non hai un account? 
        <router-link to="/register">Registrati qui</router-link>
      </div>
    </form>
  </div>

</div>
</template>

<script setup lang="ts">
import { ref, defineEmits } from 'vue';
import { useAuthStore } from '../../storage/auth';
import router from '@/router';
import { login as loginService } from '../../services/authService';

const authStore = useAuthStore();

const username = ref('');
const password = ref('');
const error = ref('');

const emit = defineEmits(['login-success']);

async function handleLogin() {
  //try {
    //const jwt = await loginService(username.value, password.value);
    if (username.value === 'admin' && password.value === 'password'){

      authStore.login(username.value, 'JWTPLACEHOLDER');
      error.value = '';
      router.push('/home');
    }
  //} catch (e) {
  //  error.value = 'Nome utente o password non validi';
  //}
}
</script>

<style scoped>
.login-container {
  max-width: 340px;
  margin: 50px auto;
  padding: 32px 24px;
  border-radius: 16px;
  background: linear-gradient(135deg, #ffffff 70%, #e3eafc 100%);
  box-shadow: 0 8px 32px rgba(10,36,59,0.15), 0 1.5px 4px rgba(10,36,59,0.10);
  border: none;
  transition: box-shadow 0.2s;
}


.login-container:hover {
  box-shadow: 0 12px 40px rgba(10,36,59,0.22), 0 2px 8px rgba(10,36,59,0.13);
}

.login-container h2 {
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
.logo-container{
  margin-top: 30px;
  display: flex;
  justify-content: center; /* Allinea orizzontalmente al centro */
  align-items: center; /* Allinea verticalmente al centro */
}

img {
  max-width: 100%;
  width: 220px;
  height: auto;
  display: block;
  justify-content: center;
  align-items: center;
}

.register-link {
  text-align: center;
  margin-top: 16px;
  color: #0a243b;
  font-size: 14px;
}

.register-link a {
  color: #007bff;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.2s;
}

.register-link a:hover {
  color: #0056b3;
  text-decoration: underline;
}
</style>


