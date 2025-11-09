import api from '@/services/url'

export async function fetchAtleti() {
  const res = await api.get('atleti/list')
  return res.data
}

export default {
  fetchAtleti
}