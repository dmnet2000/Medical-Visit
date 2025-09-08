import { Quasar, QCard, QCardSection, QForm, QInput, QBtn, QTable } from 'quasar'
import quasarLang from 'quasar/lang/it'
import '@quasar/extras/material-icons/material-icons.css'
import 'quasar/dist/quasar.css'

export default {
  install(app) {
    app.use(Quasar, {
      lang: quasarLang,
    })
    // Registra i componenti usati globalmente
    app.component('q-card', QCard)
    app.component('q-card-section', QCardSection)
    app.component('q-form', QForm)
    app.component('q-input', QInput)
    app.component('q-btn', QBtn)
    app.component('q-table', QTable)
  }
}