import { defineStore } from 'pinia'

export interface AuthUser {
  username: string
  token: string
  tipoUtente: string
  roles: string[]
  userId: number | null
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isAuthenticated: false,
    user: null as AuthUser | null,
  }),
  
  getters: {
    // Controlla se l'utente ha un ruolo specifico
    hasRole: (state) => (role: string) => {
      return state.user?.roles.includes(role) ?? false
    },
    
    // Controlla se è admin
    isAdmin: (state) => {
      return state.user?.roles.includes('ADMIN') ?? false
    },
    
    // Token JWT
    token: (state) => state.user?.token ?? null,
  },
  
  actions: {
    login(authResponse: AuthUser) {
      this.isAuthenticated = true
      this.user = authResponse
      
      // Salva nel localStorage
      localStorage.setItem('auth_user', JSON.stringify(authResponse))
    },
    
    logout() {
      this.isAuthenticated = false
      this.user = null
      
      // Rimuovi da localStorage
      localStorage.removeItem('auth_user')
    },
    
    // Ripristina autenticazione da localStorage
    restoreAuth() {
      const savedUser = localStorage.getItem('auth_user')
      if (savedUser) {
        try {
          const user = JSON.parse(savedUser) as AuthUser
          this.isAuthenticated = true
          this.user = user
        } catch (e) {
          this.logout()
        }
      }
    },
    
    // Controlla se il token è ancora valido
    checkTokenValidity() {
      if (!this.user?.token) {
        this.logout()
        return false
      }
      return true
    }
  }
})