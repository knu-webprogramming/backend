import * as serverApi from "./server_fetch.js"
import {getToken, getUserId} from "./token.js";
import {getClusters} from "./cluster_fetch.js";

//TODO 추후 게임 서버 옵션 API 조회하여 동적으로 생성하도록 한다.
const OPTION_FORM = {
    game_id: 1,
    member_id: getUserId(),
    term: 'FIXED',
    slot: 16,
    cluster_id: null,
    days: 30,
}

let SERVER_TYPE = {
    SMALL:'SMALL',
    MIDDLE:'MIDDLE' ,
    MAX:'MAX',
}


document.querySelectorAll('.buy-btn').forEach(btn => {
    btn.addEventListener('click', async () => {
        const orderType = btn.dataset.orderType;
        console.log(`order type : ${orderType}`);

        if (getUserId()) {
            const option = createOption(orderType);
            await serverApi.createServer(option);
        } else {
            window.alert('로그인이 필요한 서비스입니다!');
            location.href = '/login';
        }


    })
})


function createOption(type, option) {
    const path = window.location.pathname;
    const splits = path.split('/');
    const gameId = splits[splits.length - 1];

    // TODO Member 매핑 로직 추가.

    // OPTION_FORM 매핑
    let requestOption = Object.assign(OPTION_FORM);
    requestOption.game_id = gameId;
    requestOption.cluster_id = getSelectedCluster();
    switch (type) {
        case 'SMALL':
            requestOption.slot = 4;
            break;
        case 'MIDDLE':
            requestOption.slot = 16;
            break;
        case 'MAX':
            requestOption.slot = 32;
            break;
        case 'CUSTOM':
            requestOption = option;
            break;
        default:
            console.error('정의 되지 않은 타입입니다!');
    }

    return requestOption;
}


document.addEventListener('DOMContentLoaded', async () => {
    const select = document.querySelector('#cluster-selector');
    const clusters = await getClusters();

    clusters.forEach(cluster => {
        const option = document.createElement('option');
        option.value = cluster.id;
        const location = cluster.location.toLowerCase();
        option.style.backgroundImage = `url(/images/location/${location}.png)`
        option.innerHTML = `${cluster.location} - ${cluster.name}`;
        select.appendChild(option);
    });

});

function getSelectedCluster() {
    const selectedOption = document.querySelector('#cluster-selector option:checked');
    return selectedOption.value;
}
