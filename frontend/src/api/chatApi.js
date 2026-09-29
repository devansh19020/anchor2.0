import { apiRequest } from './client';

export const chatApi = {
  /**
   * Send a question about a repository workspace.
   * @param {Object} payload - { question: string, workspaceId: string }
   * @returns {Promise<{ answer: string, sources: string[] }>}
   */
  async sendQuestion(question, workspaceId) {
    return apiRequest('/api/chat', {
      method: 'POST',
      body: {
        question,
        workspaceId,
      },
    });
  },
};
