
let enableResetBtn = true;

const ResetDataButton = (() => {
    let Http = new XMLHttpRequest();
    let url=`${window.location.protocol}//${window.location.host}/api/reset-all-data`;
    Http.open("POST", url);
    Http.setRequestHeader('Content-type', 'application/json; charset=UTF-8');
    Http.setRequestHeader('Authorization', 'Bearer ' + getCookie("access_token"));
    Http.send();

    Http.onreadystatechange = (e) => {
        if(Http.readyState === XMLHttpRequest.DONE) {
            if(Http.responseText) {
                console.log(Http.responseText);
                window.location.reload();
            }
        }
    }
});

if(enableResetBtn) {
    let resetBtn = document.createElement("button");
    resetBtn.innerHTML = "Reset";
    resetBtn.id = "reset-btn";
    resetBtn.classList.add("reset-btn");
    resetBtn.onclick = (() => {
        ResetDataButton();
    });

    document.body.appendChild(resetBtn);
}

getCookie = ((cookieName) => {
    let cookie = {};
    document.cookie.split(';').forEach(function(el) {
        let [key,value] = el.split('=');
        cookie[key.trim()] = value;
    })
    return cookie[cookieName];
});