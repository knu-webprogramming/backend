import {createCluster, getClusters} from "./cluster_fetch.js";

document.querySelector('.cluster-plus')
    .addEventListener('click',() => {
        console.log('Plus Click!');
        const modal = document.querySelector('.modal-area');
        modal.style.display = 'flex';

        // close btn
        modal.querySelector('.window__btn')
            .addEventListener('click', () => {
               modal.style.display = 'none';
            });

        // create cluster
        modal.querySelector('#create-cluster-btn')
            .addEventListener('click', async () => {
                const data = getClusterByForm();
                await createCluster(data);
                window.alert('클러스터 생성 완료!');
                await setClusterInfo();
                modal.style.display = 'none';
        });

    });

document.addEventListener('DOMContentLoaded', async () => {
    await setClusterInfo();
});

function getClusterByForm() {
    const clusterData = {
        name: document.querySelector('input[name="name"]').value,
        application_type: document.querySelector('#application-select').value,
        deploy_type: document.querySelector('#deploy-select').value,
        location: document.querySelector('#location-select').value,
        description: document.querySelector('input[name="description"]').value,
        external_ip: document.querySelector('input[name="external_ip"]').value,
        domain: document.querySelector('input[name="domain"]').value
    };

    return clusterData;
}

async function setClusterInfo() {
    const tbody = document.querySelector('.cluster-tbody');
    // 내용 초기화
    tbody.innerHTML = '';
    const noContent = document.querySelector('.no-content');

    const clusters = await getClusters();

    if (!clusters || clusters.length == 0) {
        console.log('클러스터가 없습니다!');
        return
    }

    noContent.style.display = 'none';
    clusters.forEach(cluster => {
        const tr = document.createElement('tr');

        const idTd = document.createElement('td');
        idTd.textContent = cluster['id'];
        tr.appendChild(idTd);

        const nameTd = document.createElement('td');
        nameTd.textContent = cluster['name'];
        tr.appendChild(nameTd);

        const appTd = document.createElement('td');
        appTd.textContent = cluster['application_type'];
        tr.appendChild(appTd);

        const deployTd = document.createElement('td');
        deployTd.textContent = cluster['deploy_type'];
        tr.appendChild(deployTd);

        const createTd = document.createElement('td');
        createTd.textContent = cluster['created_at'];
        tr.appendChild(createTd);

        //TODO 클러스터 상세 페이지
        // tr.addEventListener('click', () => {
        //     location.href = `/clusters/${cluster['id']}`;
        // })

        tbody.appendChild(tr);

    });
}
