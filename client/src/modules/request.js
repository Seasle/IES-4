import { API_URL } from '@/modules/constants.js';

export const get = async url => {
    const response = await fetch(`${API_URL}${url}`);
    const data = await response.json();

    return data;
};