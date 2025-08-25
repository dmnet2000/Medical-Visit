// src/services/authService.ts
import axios from 'axios';

export async function login(username: string, password: string): Promise<string> {
  // Replace with your real API endpoint
  const response = await axios.post('/api/auth/login', { username, password });
  // Assuming the JWT is returned as response.data.token
  return response.data.token;
}
