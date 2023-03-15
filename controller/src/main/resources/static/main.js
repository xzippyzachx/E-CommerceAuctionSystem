// Try to set up WebSocket connection with the handshake at "http://localhost:8080/stomp"
let sock = new SockJS("http://localhost:8080/stomp");

// Create a new StompClient object with the WebSocket endpoint
let client = Stomp.over(sock);
client.debug = null;

// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
client.connect({}, frame => {

    client.subscribe("/broadcast/auction-update/1", payload => {
        let num = document.getElementById('number1');
        let data = JSON.parse(payload.body);
        let numText = data.auc_current_price + " " + data.auc_state;
        num.innerHTML = numText;
    });

    client.subscribe("/broadcast/auction-update/2", payload => {
        let num = document.getElementById('number2');
        let data = JSON.parse(payload.body);
        let numText = data.auc_current_price + " " + data.auc_state;
        num.innerHTML = numText;
    });

    client.subscribe("/broadcast/auction-update/3", payload => {
        let num = document.getElementById('number3');
        let data = JSON.parse(payload.body);
        let numText = data.auc_current_price + " " + data.auc_state;
        num.innerHTML = numText;
    });

});