import { getToken } from './token.js';

export function fetchApi(endpoint, method = 'GET', body = null) {
    if (endpoint[0] !== '/') {
        endpoint = `/${endpoint}`;
    }

    const token = getToken();
    const url = `/api${endpoint}`;
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
    };

    if (token === null || token === undefined) {
        delete headers.Authorization;
    }

    const config = { method, headers };
    if (body !== null) {
        config['body'] = JSON.stringify(body)
    }

    return fetch(url, config)
        .then(async (res) => {
            const statusCode = res.status;
            if (res.ok) {
                // const location = res.headers.get('location');
                if (res.status !== 204 && res.body != null && res.body != undefined) {
                    return await res.json();
                }
            } else {
                return { statusCode: statusCode };
            }
        })
        .catch((error) => {
            console.error(`fetch error : ${error}`);
        });
}
