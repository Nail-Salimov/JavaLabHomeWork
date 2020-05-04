<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <script src="/webjars/jquery/3.1.0/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>

    <script>
        function connect() {
            var socket = new SockJS('localhost:8080/messages');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log("connected: " + frame);
                stompClient.subscribe('/topic/chat', function(response) {

                   alert(response);
                });
            });
        }

        function draw(side, text) {
            console.log("drawing...");
            alert()

        }
        function disconnect(){
            stompClient.disconnect();
        }
        function sendMessage(){
            stompClient.send("/app/test", {}, "Hellooooo");

        }
    </script>

</head>
<body>
<h1>Simple chat</h1>
<div class="chat_window">
    <div class="top_menu">
        <div class="buttons">
            <div class="button close"></div>
            <div class="button minimize"></div>
            <div class="button maximize"></div>
        </div>
        <div class="title">Chat</div>
    </div>
    <ul class="messages"></ul>
    <div class="bottom_wrapper clearfix">
        <div class="message_input_wrapper">
            <input id="message_input_value" class="message_input" placeholder="Type your message here..." />
        </div>
        <div class="send_message">
            <div class="icon"></div>

        </div>

        <button onclick="connect()">Connect to chat</button>
        <button onclick="sendMessage()" class="text">Send</button>
        <button onclick="disconnect()">Disconnect from chat</button>
    </div>
</div>
<div id="message_template" class="message_template">
    <li class="message"><div class="avatar"></div>
        <div class="text_wrapper">
            <div class="text"></div>
        </div></li>
</div>
</body>
</html>