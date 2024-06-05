import * as commApi from './common_fetch.js';

const PREFIX_URI = '/servers'


export async function getServer(serverId) {
    const endpoint = `${PREFIX_URI}/${serverId}`;
    return await commApi.fetchApi(endpoint);
}

export async function getServers(memberId) {
    let endpoint = PREFIX_URI
    if (memberId) {
        const params = {
            memberId: memberId
        }
        const query = new URLSearchParams(params).toString();
        endpoint = `${endpoint}?${query}`
    }
    console.log(endpoint);
    return await commApi.fetchApi(endpoint);
}

export async function createServer(option) {
    const endpoint = `${PREFIX_URI}`;

    //TODO 요청 후 결과 반환
    const result = await commApi.fetchApi(endpoint,'POST',option);
    if (result) {
        window.alert(`서버 생성 요청에 실패하였습니다.`);
    } else {
        window.alert(`서버 생성 요청이 완료되었습니다!`);
    }
    //TODO 추가 로직

}

export async function restartServer(serverId) {
    const endpoint = `${PREFIX_URI}/${serverId}/restart`;
    return await commApi.fetchApi(endpoint);
}

//TODO 삭제 말고 정지에 관한 요청이 추가로 필요한가??
export async function deleteServer(serverId) {
    const endpoint = `${PREFIX_URI}/${serverId}`;
    return await commApi.fetchApi(endpoint, 'DELETE');
}

export async function getServerControls(serverId) {
    const endpoint = `${PREFIX_URI}/${serverId}/controls`;
    return await commApi.fetchApi(endpoint);
}
