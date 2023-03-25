const tableElement = document.getElementById("auctions-table");
const searchInput = document.getElementById("search-input");

const UpdateAuctions = (() => {
    for (let i = 0; i < tableElement.children.length; i++) {
        let tableChild = tableElement.children[i];
        let countdownChild = tableChild.querySelector(".auction-countdown");
        let buttonChild = tableChild.querySelector(".auction-btn");
        let auc_id = Number.parseInt(tableChild.id.slice(7));

        if(countdownChild != null && countdownChild.innerHTML !== "")
            CountDown(countdownChild.innerHTML, countdownChild);

        if(buttonChild != null) {
            buttonChild.addEventListener("click", () => {
                switch (buttonChild.value) {
                    case "running":
                        window.location.href = `http://localhost:8080/bidding?auc_id=${auc_id}`
                        break;
                    case "complete":
                        window.location.href = `http://localhost:8080/payment?auc_id=${auc_id}`
                        break;
                    case "paid":
                        window.location.href = `http://localhost:8080/receipt?auc_id=${auc_id}`
                        break;
                }
            })
        }
    }
});
UpdateAuctions();

const SearchAuctions = (() => {
    let Http = new XMLHttpRequest();
    let url='http://localhost:8080/api/get-auctions-by-key';
    Http.open("POST", url);
    Http.setRequestHeader('Content-type', 'application/json; charset=UTF-8');
    Http.setRequestHeader('Authorization', 'Bearer ' + getCookie("access_token"));

    let payload = {
        keyword: searchInput.value
    };

    Http.send(JSON.stringify(payload));
    Http.onreadystatechange = (e) => {
        if(Http.readyState === XMLHttpRequest.DONE) {
            if(Http.responseText) {
                BuildAuctions(JSON.parse(Http.responseText));
            }
        }
    }
});
searchInput.addEventListener('keypress', (event) => {
    if (event.key === "Enter") {
        event.preventDefault();
        SearchAuctions();
    }

});

const BuildAuctions = ((data) => {

    tableElement.innerHTML = `<tr>
        <th>Item Name</th>
        <th>Current Price</th>
        <th>Type</th>
        <th>Time Left</th>
        <th>Status</th>
        <th></th>
        </tr>`;

    for (let a in data) {
        let auction = data[a];

        let btnBame = "bid";
        switch (auction.auc_state) {
            case "running":
                btnBame = "Bid";
                break;
            case "complete":
                btnBame = "Pay";
                break;
            case "paid":
                btnBame = "View";
                break;
        }

        tableElement.insertAdjacentHTML('beforeend',`
        <tr id="auc-id-${auction.auc_id}">
            <td>
                <span>${auction.auc_itm_id.itm_name}</span>
            </td>
            <td>
                <span>$${auction.auc_current_price}</span>
            </td>
            <td>
                <span>${auction.auc_type}</span>
            </td>
            <td>
                <span class="auction-countdown">${auction.fwd_end_time ? auction.fwd_end_time : ''}</span>
            </td>
            <td>
                <span>${auction.auc_state}</span>
            </td>
            <td>
                <button class="auction-btn" value="${auction.auc_state}">${btnBame}</button>
            </td>
        </tr>
        `)
    }

    UpdateAuctions();
});

getCookie = ((cookieName) => {
    let cookie = {};
    document.cookie.split(';').forEach(function(el) {
        let [key,value] = el.split('=');
        cookie[key.trim()] = value;
    })
    return cookie[cookieName];
});