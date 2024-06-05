import * as gameApi from './game_fetch.js';

document.addEventListener('DOMContentLoaded', function() {
    const cardDiv = document.querySelector('.card-div');
    const template = document.querySelector('#card-template');


    gameApi.getGames().then(games => {
        games.forEach(game => {
            const clone = document.importNode(template.content, true);

            const heroImage = clone.querySelector('.hero-image');
            heroImage.src = game.img_url;
            heroImage.alt = 'Game image';

            const gameNameLink = clone.querySelector('main.card-body h1');
            gameNameLink.textContent = game.name;

            const gameDescription = clone.querySelector('main.card-body p');
            gameDescription.textContent = game.description;

            // linked Detail Page
            const link = `games/${game.id}`;

            clone.querySelector('.card-container').addEventListener('click', function() {
                if (game.is_active === true) {
                    location.href = link;
                } else {
                    window.alert("현재 미 지원 게임입니다!");
                }
            }, false);

            const cardContainer = clone.querySelector('.card-container');
            if (game.is_active === true) {
                cardContainer.classList.add('active');
            } else {
                cardContainer.classList.add('inactive');
            }

            cardDiv.appendChild(clone);
        });
    })
});


async function fetchGames() {
    try {
        const response = await fetch('/api/games');

        if (!response.ok) {
            throw new Error(`Error! status: ${response.status}`);
        }

        const games = await response.json();
        console.log('Fetched games:', games);

        // 여기에서 games 데이터를 처리합니다.
        // 예를 들어, 웹 페이지에 목록을 동적으로 표시할 수 있습니다.

        return games;

    } catch (error) {
        console.error('There was a problem fetching the game list:', error);
    }
}


