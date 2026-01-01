import api from '@/services/url'

export async function fetchAllenatori() {
  const res = await api.get('allenatori/list')
  return res.data
}

export async function fetchAnni() {
  const res = await api.get('anno/list')
  return res.data
}

export async function fetchSquadre() {
  const res = await api.get('squadra/listaSquadre')
  return res.data
}

export async function createSquadra(payload: { nome: string; idAllenatore: number; idAnnoAgonistico: number }) {
  const res = await api.post('squadra/insert', payload)
  return res.data
}

export async function deleteSquadra(id: number) {
  return api.delete(`squadra/delete/${id}`)
}

export async function fetchSquadraById(id:number) {
  const res = await api.get(`squadra/getDetail/${id}`)
  return res.data
}

export async function associaAtleti(squadraId:number, atletiIds:number[]) {
  // Endpoint backend esempio: POST /squadra/{id}/associa-atleti  body: [idAtleta,...]
  const res = await api.post(`squadra/${squadraId}/associa-atleti`, atletiIds)
  return res.data
}

export default {
  fetchAllenatori,
  fetchAnni,
  fetchSquadre,
  createSquadra,
  deleteSquadra,
  fetchSquadraById,
  associaAtleti
}