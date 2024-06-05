import * as commApi from "./common_fetch.js";

export async function getMember(memberId) {
    const endpoint = `/members/${memberId}`
    return await commApi.fetchApi(endpoint);
}
