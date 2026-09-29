import { apiRequest } from './client';

export const repositoryApi = {
  /**
   * Import a GitHub repository for indexing.
   * @param {Object} payload - { repositoryUrl }
   * @returns {Promise<RepositoryResponse>}
   */
  async importRepository(repositoryUrl) {
    return apiRequest('/api/repositories/import', {
      method: 'POST',
      body: { repositoryUrl },
    });
  },

  /**
   * Fetch all imported repositories for the current user.
   * @returns {Promise<RepositoryResponse[]>}
   */
  async getRepositories() {
    return apiRequest('/api/repositories', {
      method: 'GET',
    });
  },

  /**
   * Get the current indexing status of a repository.
   * @param {string} repositoryId
   * @returns {Promise<{ repositoryId: string, status: string, message: string, updatedAt: string }>}
   */
  async getRepositoryStatus(repositoryId) {
    return apiRequest(`/api/repositories/${repositoryId}/status`, {
      method: 'GET',
    });
  },

  /**
   * Check user access to a specific workspace ID.
   * @param {string} workspaceId
   * @returns {Promise<boolean>}
   */
  async checkWorkspaceAccess(workspaceId) {
    return apiRequest(`/api/repositories/workspaces/${workspaceId}/access`, {
      method: 'GET',
    });
  },
};
