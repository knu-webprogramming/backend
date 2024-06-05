import * as commApi from "./common_fetch.js";

const PREFIX_URI = '/clusters'

export async function getCluster(clusterId) {
    const endpoint = `${PREFIX_URI}/${clusterId}`;
    return await commApi.fetchApi(endpoint);
}

export async function getClusters() {
    const endpoint = `${PREFIX_URI}`;
    return await commApi.fetchApi(endpoint);
}

export async function createCluster(data) {
    const endpoint = `${PREFIX_URI}`;
    return await commApi.fetchApi(endpoint, 'POST', data);
}

export async function updateCluster(clusterId, data) {
    const endpoint = `${PREFIX_URI}/${clusterId}`;
    return await commApi.fetchApi(endpoint, 'PUT', data);
}
