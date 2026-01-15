import axios from 'axios'
import { useAuthStore } from '@/storage/auth'
import router from '@/router'

const api = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 10000,
})

// Interceptor per aggiungere JWT token ad ogni richiesta
api.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore()
    const token = authStore.token
    
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Interceptor per gestire errori di autenticazione
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Token scaduto o non valido
      const authStore = useAuthStore()
      authStore.logout()
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

export default api