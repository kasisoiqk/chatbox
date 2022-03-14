const url = "http://localhost:8080";
var stompClient = null;
var username = null;

var senderId = 1;
var recipientId = 2;

var colors = [
  '#2196F3', '#32c787', '#00BCD4', '#ff5652',
  '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

var messageInput = document.querySelector("#message-input");

function connect(event) {
  var socket = new SockJS(url + '/chat');
  stompClient = Stomp.over(socket);

  stompClient.connect({}, onConnected, onError);
  event.preventDefault();
}

function onConnected(frame) {
  // Subscribe to the Public Topic
  console.log("Connected to: " + frame);
  stompClient.subscribe('/topic/messages/' + senderId, onMessageReceived);
}

function onError(error) {
  console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
}

function sendMessage(event) {
  var messageContent = messageInput.value.trim();
  if(messageContent && stompClient) {
      var message = {
          type: 'ONLY',
          content: messageContent,
          senderId: senderId,
          recipientId: recipientId
      };
      stompClient.send("/app/messages/chat.sendMessage/" + recipientId, {}, JSON.stringify(message));
      messageInput.value = '';
  }
  event.preventDefault();
}

function onMessageReceived(payload) {
  var message = JSON.parse(payload.body);
  console.log(message);
}

function getAvatarColor(messageSender) {
  var hash = 0;
  for (var i = 0; i < messageSender.length; i++) {
      hash = 31 * hash + messageSender.charCodeAt(i);
  }
  var index = Math.abs(hash % colors.length);
  return colors[index];
}