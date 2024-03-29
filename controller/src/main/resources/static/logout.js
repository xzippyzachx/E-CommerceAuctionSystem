
let enableLogoutBtn = true;

const LogoutButton = (() => {
    let Http = new XMLHttpRequest();
    let url=`${window.location.protocol}//${window.location.host}/logout`;
    Http.open("POST", url);
    Http.setRequestHeader('Content-type', 'application/json; charset=UTF-8');
    Http.send();

    document.cookie = "access_token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    window.location.href = `${window.location.protocol}//${window.location.host}/login`;
});

if(enableLogoutBtn) {
    let logoutBtn = document.createElement("button");
    logoutBtn.innerHTML = "Logout";
    logoutBtn.id = "logout-btn";
    logoutBtn.classList.add("logout-btn");
    logoutBtn.onclick = (() => {
        LogoutButton();
    });

    document.body.appendChild(logoutBtn);
}