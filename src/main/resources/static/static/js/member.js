import {getServers} from "./server_fetch.js";
import {getUserId} from "./token.js";
import {getMember} from "./member_fetch.js";


document.addEventListener('DOMContentLoaded',async () => {
    const memberId = await getUserId();
    const serverTbody = document.querySelector('.server-tbody');
    const serverNoContent = document.querySelector('.no-content');
    if (memberId) {
        const servers = await getServers(memberId);
        if (!servers || servers.length == 0) {
            return
        }

        serverNoContent.style.display = 'none';
        servers.forEach(server => {
            // 새로운 행 생성
            const tr = document.createElement('tr');
            // 데이터 객체의 각 속성에 대해 셀 생성

            const gameTd = document.createElement('td');
            gameTd.textContent = server['game'];
            tr.appendChild(gameTd);
            const serverIdTd = document.createElement('td');
            serverIdTd.textContent = server['id'];
            tr.appendChild(serverIdTd);
            const slotTd = document.createElement('td');
            slotTd.textContent = server['slot'];
            tr.appendChild(slotTd);
            const dayTd = document.createElement('td');
            dayTd.textContent = server['days'];
            tr.appendChild(dayTd);
            const createTd = document.createElement('td');
            createTd.textContent = server['created_at'];
            tr.appendChild(createTd);

            tr.addEventListener('click', () => {
                location.href = `/servers/${server['id']}`;
            })

            // for (const td in tr.querySelectorAll('td')) {
            //     td.classList.add('noBorder'); // 클래스 추가, 필요에 따라 조정 가능
            // }
            serverTbody.appendChild(tr); // 완성된 행을 tbody에 추가
        });
    }
});


document.addEventListener('DOMContentLoaded', async () => {
    const memberId = await getUserId();
    const member = await getMember(memberId);
    const rows = document.querySelectorAll('.user-info-row');
    rows.forEach(row => {
        const input = row.querySelector('input');
        const name = input.getAttribute('name');
        input.value = member[name];
    });
});
