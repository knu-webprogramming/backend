import {getToken, deleteToken, getRole} from "./token.js";


const body = document.querySelector('body'),
    sidebar = body.querySelector('nav'),
    toggle = body.querySelector(".toggle"),
    loginBtn = body.querySelector('#login-btn'),
    logoutBtn = body.querySelector('#logout-btn'),
    mypageBtn = body.querySelector('#mypage-btn');
document.addEventListener("DOMContentLoaded", function() {

        // searchBtn = body.querySelector(".search-box"),
        // modeSwitch = body.querySelector(".toggle-switch"),
        // modeText = body.querySelector(".mode-text");

    toggle.addEventListener("click" , () => {
        sidebar.classList.toggle("close");
    });

    const admins = document.querySelectorAll('.admin');
    if(getToken()) {
        loginBtn.style.display = 'none';

        const role = getRole();

        if (role !== 'ADMIN') {
            admins.forEach((el) => {
                el.style.display = 'none';
            });
        }

    }else {
        logoutBtn.style.display = 'none';
        mypageBtn.style.display = 'none';

        admins.forEach((el) => {
            el.style.display = 'none';
        });
    }



    // searchBtn.addEventListener("click" , () => {
    //     sidebar.classList.remove("close");
    // });

    // modeSwitch.addEventListener("click" , () => {
    //     body.classList.toggle("dark");
    //
    //     if(body.classList.contains("dark")){
    //         modeText.innerText = "Light mode";
    //     }else{
    //         modeText.innerText = "Dark mode";
    //     }
    // });
});


logoutBtn.addEventListener('click', () => {
    deleteToken();
    location.href = '/login';
})
