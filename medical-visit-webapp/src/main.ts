import { createApp } from 'vue'
import App from './App.vue'
import quasar from './plugins/quasar'
import router from './router'
import { createPinia } from 'pinia'

async function bootstrap() {
  try {
    const res = await fetch('/app-config')
    if (res.ok) {
      (window as any).__APP_CONFIG__ = await res.json()
    } else {
      (window as any).__APP_CONFIG__ = {}
    }
  } catch (e) {
    (window as any).__APP_CONFIG__ = {}
    console.warn('Could not load /app-config, falling back to defaults', e)
  }

  const app = createApp(App)
  app.use(router)
  .use(quasar)
  .use(router)
  .use(createPinia())
  .mount('#app')
}

bootstrap()


createApp(App)
    .use(quasar)
    .use(router)
    .use(createPinia())
    .mount('#app')
