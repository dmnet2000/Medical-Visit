import { createApp } from 'vue'
import App from './App.vue'
import quasar from './plugins/quasar'
import router from './router'
import { createPinia } from 'pinia'

createApp(App)
    .use(quasar)
    .use(router)
    .use(createPinia())
    .mount('#app')
