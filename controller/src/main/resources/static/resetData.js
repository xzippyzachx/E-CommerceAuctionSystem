
let enableResetBtn = true;

const ResetDataButton = (() => {
    let Http = new XMLHttpRequest();
    let url='http://localhost:8080/api/reset-all-data';
    Http.open("POST", url);
    Http.setRequestHeader('Content-type', 'application/json; charset=UTF-8');
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