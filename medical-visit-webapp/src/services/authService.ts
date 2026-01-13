// src/services/authService.ts
import api from '@/services/url';

export interface RegisterRequest {
  username: string;
  password: string;
  idAllenatore?: number;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface ChangePasswordRequest {
  authId: number;
  oldPassword: string;
  newPassword: string;
}

export async function register(request: RegisterRequest) {
  const res = await api.post('api/auth/register', request);
  return res.data;
}

export async function login(request: LoginRequest) {
  const res = await api.post('api/auth/login', request);
  return res.data;
}

export async function changePassword(request: ChangePasswordRequest) {
  const res = await api.put('api/auth/change-password', request);
  return res.data;
}

export default {
  register,
  login,
  changePassword,
};
