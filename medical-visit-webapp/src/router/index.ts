import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/storage/auth'
import Home from '../component/view/Home.vue'
import Login from '@/component/view/Login.vue'
import NotFound from '@/component/view/NotFound.vue'
import Import from '@/component/search/Import.vue'
import SearchForm from '@/component/search/SearchForm.vue'
import ModifyAtleta from '@/component/crud/ModifyAtleta.vue'
import CoachManager from '@/component/view/CoachManager.vue'
import AnnoAgonisticoManager from '@/component/view/AnnoAgonisticoManager.vue'
import SquadreManager from '@/component/view/SquadreManager.vue'
import SquadraAtleti from '@/component/view/SquadraAtleti.vue'
import Register from '@/component/view/Register.vue'

const routes = [
    { 
      path: '/home', 
      component: Home,
      meta: { requiresAuth: true },
      children: [ 
        { path: 'import', component: Import },
        { path: 'coach', component: CoachManager },
        { path: 'search', component: SearchForm },
        { path: 'search/:id/modifica', component: ModifyAtleta },
        { path: 'anno', component: AnnoAgonisticoManager },
        { path: 'squadre', component: SquadreManager },
        { path: 'squadre/:id/atleti', name: "AssociaAtleti", component: SquadraAtleti, props: true }
      ]
    },
    { path: '/login', component: Login, meta: { guest: true } },
    { path: '/register', component: Register, meta: { guest: true } },
    { path: '/', redirect: '/login' },
    { path: '/:pathMatch(.*)', name: 'bad-not-found', component: NotFound },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

// Navigation guard
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  // Ripristina autenticazione da localStorage
  authStore.restoreAuth()
  
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  const guestOnly = to.matched.some(record => record.meta.guest)
  
  if (requiresAuth && !authStore.isAuthenticated) {
    // Rotta protetta ma utente non autenticato
    next('/login')
  } else if (guestOnly && authStore.isAuthenticated) {
    // Rotta per guest ma utente già autenticato
    next('/home')
  } else {
    next()
  }
})

export default router