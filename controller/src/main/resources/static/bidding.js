// Try to set up WebSocket connection with the handshake at "http://localhost:8080/stomp"
let sock = new SockJS("http://localhost:8080/stomp");

// Create a new StompClient object with the WebSocket endpoint
let client = Stomp.over(sock);
client.debug = null;

const completeMessageElement = document.getElementById("bid-message");
const currentPriceElement = document.getElementById("current-price");
const bidAmountElement = document.getElementById("bid-amount");
const formElement = document.getElementById("bid-form");

// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
client.connect({}, frame => {

    client.subscribe(`/broadcast/auction-update/${auc_id}`, payload => {

        let data = JSON.parse(payload.body);
        let currentPrice = data.auc_current_price;
        let state = data.auc_state;

        if(state == "running") {
            currentPriceElement.innerHTML = "Current Price: $" + currentPrice.toFixed(2);
            formElement.classList.remove("hide");
            bidAmountElement.value = currentPrice + 10;
        } else {
            formElement.classList.add("hide");
            completeMessageElement.innerHTML = "Auction is complete!";
        }

    });
    console.log("Listening to auction ID: " + auc_id);

});

const BidButton = (() => {
    let Http = new XMLHttpRequest();
    let url='http://localhost:8080/api/new-bid';
    Http.open("POST", url);
    Http.setRequestHeader('Content-type', 'application/json; charset=UTF-8');

    let payload = {
        auc_id: auc_id,
        bid_amount: bidAmountElement.value
    };

    Http.send(JSON.stringify(payload));
    Http.onreadystatechange = (e) => {
        if(Http.readyState === XMLHttpRequest.DONE) {
            if(Http.responseText) {
                completeMessageElement.innerHTML = Http.responseText;
            }
        }
    }
});

window.onpopstate = function() {
    window.location.href = "http://localhost:8080/auctions"
};
history.pushState({}, '');