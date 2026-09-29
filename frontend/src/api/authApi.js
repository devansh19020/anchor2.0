import { apiRequest } from './client';

export const authApi = {
  /**
   * Register a new user account.
   * @param {Object} payload - { name, email, password }
   * @returns {Promise<{ token: string, userId: string, name: string, email: string }>}
   */
  async register(payload) {
    return apiRequest('/api/users/register', {
      method: 'POST',
      body: payload,
    });
  },

  /**
   * Login to an existing account.
   * @param {Object} payload - { email, password }
   * @returns {Promise<{ token: string, userId: string, name: string, email: string }>}
   */
  async login(payload) {
    return apiRequest('/api/users/login', {
      method: 'POST',
      body: payload,
    });
  },

  /**
   * Fetch current authenticated user details.
   * @returns {Promise<{ userId: string, name: string, email: string, role: string }>}
   */
  async getCurrentUser() {
    return apiRequest('/api/users/me', {
      method: 'GET',
    });
  },
};
