var ws;

function connect() {
    var uid = document.getElementById("uid").value;
    var wsserver = document.getElementById("wsserver").value;
    var url = wsserver + uid;
    //var url = "ws://echo.websocket.org";

    ws = new WebSocket(url);

    ws.onmessage = function(event) { // Called when client receives a message from the server
        console.log(event.data);

        // display on browser
        var log = document.getElementById("log");
        log.innerHTML += "message from server: " + event.data + "\n";
    };

    ws.onopen = function(event) { // called when connection is opened
        var log = document.getElementById("log");
        log.innerHTML += "Connected to " + event.currentTarget.url + "\n";
    };
}

function send() {  // this is how to send messages
    var content = document.getElementById("msg").value;
    ws.send(content);
}
