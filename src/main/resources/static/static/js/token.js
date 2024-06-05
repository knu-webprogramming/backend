const TOKEN_KEY = 'authToken';

// URL에서 토큰 추출
function getTokenFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('token');
}

// 토큰을 localStorage에 저장
const token = getTokenFromUrl();
if (token) {
    window.localStorage.setItem(TOKEN_KEY, token);
    // 필요하다면 URL에서 토큰 파라미터 제거
    window.history.replaceState(null, '', window.location.pathname);
}


export function getToken() {
    return window.localStorage.getItem(TOKEN_KEY);
}
export function deleteToken() {
    window.localStorage.removeItem(TOKEN_KEY);
}

function decodeToken(token) {
    const base64Payload = token.split('.')[1];
    const base64 = base64Payload.replace(/-/g, '+').replace(/_/g, '/');
    return JSON.parse(
        decodeURIComponent(
            window
                .atob(base64)
                .split('')
                .map(function (c) {
                    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                })
                .join('')
        )
    );
}

export function getUserId() {
    const token = getToken();
    if (!token) {
        return null;
    }
    const tokenContents = decodeToken(token);
    const userId = tokenContents['sub'];
    return userId;
}

export function getRole() {
    const token = getToken();
    const tokenContents = decodeToken(token);
    const role = tokenContents['role'];
    return role;
}

