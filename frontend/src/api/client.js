const BASE_URL = import.meta.env.VITE_API_BASE_URL || '';

export class ApiError extends Error {
  constructor(message, status, data) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
    this.data = data;
  }
}

/**
 * Core HTTP client for ANCHOR API Gateway.
 */
export async function apiRequest(endpoint, options = {}) {
  const { body, headers = {}, method = 'GET', ...customConfig } = options;

  const token = sessionStorage.getItem('anchor_token');

  const defaultHeaders = {
    'Content-Type': 'application/json',
  };

  if (token) {
    defaultHeaders['Authorization'] = `Bearer ${token}`;
  }

  const config = {
    method,
    headers: {
      ...defaultHeaders,
      ...headers,
    },
    ...customConfig,
  };

  if (body) {
    config.body = JSON.stringify(body);
  }

  const cleanBase = BASE_URL.replace(/\/$/, '');
  const url = cleanBase ? `${cleanBase}${endpoint}` : endpoint;


  try {
    const response = await fetch(url, config);

    // Handle 401 Unauthorized
    if (response.status === 401) {
      sessionStorage.removeItem('anchor_token');
      sessionStorage.removeItem('anchor_user');
      // Dispatch custom event so AuthContext can update state
      window.dispatchEvent(new CustomEvent('anchor_auth_unauthorized'));
    }

    let responseData = null;
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
      responseData = await response.json();
    } else {
      const text = await response.text();
      responseData = text ? { message: text } : null;
    }

    if (!response.ok) {
      const errorMessage =
        (responseData && responseData.message) ||
        (responseData && responseData.error) ||
        `Request failed with status ${response.status}`;
      
      throw new ApiError(errorMessage, response.status, responseData);
    }

    return responseData;
  } catch (error) {
    if (error instanceof ApiError) {
      throw error;
    }
    // Network error or unexpected exception
    throw new ApiError(
      error.message || 'Unable to connect to ANCHOR services. Please check your network connection.',
      0,
      null
    );
  }
}
