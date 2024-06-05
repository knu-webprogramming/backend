import * as commApi from "./common_fetch.js";


export async function getGame(gameId) {
    const endpoint = `/games/${gameId}`
    return await commApi.fetchApi(endpoint);
}

export async function getGames() {
    const endpoint = `/games`
    return await commApi.fetchApi(endpoint);
}

