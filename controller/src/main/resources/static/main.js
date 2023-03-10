// Try to set up WebSocket connection with the handshake at "http://localhost:8080/stomp"
let sock = new SockJS("http://localhost:8080/stomp");

// Create a new StompClient object with the WebSocket endpoint
let client = Stomp.over(sock);
client.debug = null;

// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
client.connect({}, frame => {

    client.subscribe("/topic/update", payload => {
        let num = document.getElementById('number');
        let numText = payload.body;
        num.innerHTML = numText;
    });

});