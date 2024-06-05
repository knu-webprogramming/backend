import * as commApi from "./common_fetch.js";

const BASE_PATH = '/options';
export async function getOption(serverId) {
    const endpoint = `${BASE_PATH}/${serverId}`
    return await commApi.fetchApi(endpoint);
}

export async function updateOption(serverId, game, options) {
    const endpoint = `${BASE_PATH}/${game}/${serverId}`;
    return await commApi.fetchApi(endpoint, 'PUT', options);
}

export async function resetOption(serverId) {
    const endpoint = `${BASE_PATH}/${serverId}/reset`;
    return await commApi.fetchApi(endpoint, 'PUT');
}
