import api from '@/services/url'
import type { AuthUser } from '@/storage/auth'

export interface RegisterRequest {
  username: string
  password: string
  idUser?: number | null
  tipoUtente: 'ATLETA' | 'ALLENATORE' | 'ADMIN'
}

export interface LoginRequest {
  username: string
  password: string
}

export interface ChangePasswordRequest {
  authId: number
  oldPassword: string
  newPassword: string
}

export interface AuthResponse {
  token: string
  username: string
  tipoUtente: string
  roles: string[]
  userId: number | null
}

/**
 * Registrazione nuovo utente
 */
export async function register(request: RegisterRequest): Promise<AuthResponse> {
  const res = await api.post('/api/auth/register', request)
  return res.data
}

/**
 * Login utente - Restituisce JWT token
 */
export async function login(request: LoginRequest): Promise<AuthResponse> {
  const res = await api.post('/api/auth/login', request)
  return res.data
}

/**
 * Cambio password
 */
export async function changePassword(request: ChangePasswordRequest) {
  const res = await api.put('/api/auth/change-password', request)
  return res.data
}

export default {
  register,
  login,
  changePassword,
}
