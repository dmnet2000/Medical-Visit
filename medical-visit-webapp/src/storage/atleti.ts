import { defineStore } from 'pinia'

export const useAtletaStore = defineStore('atleta', {
  state: () => ({
    selected: null as any
  }),
  actions: {
    setSelected(atleta: any) {
      this.selected = atleta
    }
  }
})