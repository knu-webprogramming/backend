import {deleteServer, getServer, getServerControls, restartServer} from "./server_fetch.js";
import {getOption, resetOption, updateOption} from "./option_fetch.js";

const path = window.location.pathname;
const split = path.split('/');
const serverId = split[split.length-1];
console.log(serverId);

//TODO 서버 설정
document.addEventListener('DOMContentLoaded',async () => {
    const server = await getServer(serverId);

    const rows = document.querySelectorAll('.server-info-row');
    rows.forEach(row => {
        const input = row.querySelector('input');
        const name = input.getAttribute('name');
        input.value = server[name];
    });


});

// TODO 환경변수
document.addEventListener('DOMContentLoaded',async () => {
    const optData = await getOption(serverId);
    const options = optData['game_option'];
    updateOptionUI(options);
});

// TODO 서버 컨트롤 로그
document.addEventListener('DOMContentLoaded',async () => {
    const controls = await getServerControls(serverId);
    let logText = '';
    controls.forEach(control => {
        const row = `[${control['created_at']}][${control['control']}] 진행상황 : ${control['progress']} | 결과 메세지 : ${control['result_message']} | 적용 시간 : ${control['applied_at']}\n`;
        logText += row;
    });
    document.querySelector('.control-logs').value = logText;
});


document.querySelector('#game-opt-update')
    .addEventListener('click', async () => {
        const game = document.querySelector('input[name="game"]').value;
        const lowGame = game.toLowerCase();

        const form = document.querySelector('.game-opt-content');
        let formData = {};
        form.querySelectorAll('input').forEach(input => {
            if (input.name !== "" && input.name !== undefined) {
                formData[input.name] = input.value.trim();
            }
        });
        await updateOption(serverId, lowGame, formData);

        const optData = await getOption(serverId);
        const options = optData['game_option'];
        updateOptionUI(options);

        window.alert('게임 옵션 업데이트 완료!');
});

document.querySelector('#game-opt-reset')
    .addEventListener('click', async () =>{
        await resetOption(serverId);
        const optData = await getOption(serverId);
        const options = optData['game_option'];
        updateOptionUI(options);
        window.alert('게임 옵션 초기화 완료!');
    });



/**
 * 서버 컨트롤 버튼 매핑
 */
// document.querySelector('#start-btn').addEventListener('click', () => {
//     window.alert("흐음....")
// });
document.querySelector('#restart-btn').addEventListener('click', async () => {
    await restartServer(serverId);
    window.alert('재시작 요청 완료');
});
document.querySelector('#delete-btn').addEventListener('click', async () => {
    await deleteServer(serverId);
    window.alert('삭제 요청 완료');
});


function updateOptionUI(options) {
    const content = document.querySelector('.game-opt-content');
    const row = content.querySelector('.game-opt-row');

    Object.entries(options).forEach(([key, value]) => {
        const cloneRow = row.cloneNode(true);
        const label = cloneRow.querySelector('label');
        label.setAttribute('for',key);
        label.textContent = key;
        const input = cloneRow.querySelector('input');
        input.value = value;
        input.name = key;
        content.appendChild(cloneRow);
    });

    row.style.display = 'none';
}
