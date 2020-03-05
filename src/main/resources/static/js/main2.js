var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var chatHeader = document.querySelector('#chat-header');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var onlineList = document.querySelector('#online-list');
var stompClient = null;
var username = null;

var subscribedChannel = null;

var receiver = null;

// Message Queue, IMPORTANT
var MQ = {publicQueue: []};
const MAX_MESSAGE_IN_QUEUE = 50;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function setCustomTopic() {
    var customTopicWidget = document.querySelector("#custom-topic-input");
    subscribedChannel = '/'+customTopicWidget.value.trim();
    console.log(subscribedChannel);
}

function selectTopic(element) {
    try {
        document.querySelector('#topic .pill.selected').classList.remove("selected");
    } catch(e) {} finally {
        element.classList.add("selected");
        subscribedChannel = '/'+element.getAttribute("name");
        console.log(subscribedChannel);
    }
}

function connect() {
    username = document.querySelector('#name').value.trim();

    if(username && subscribedChannel) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
        console.log("Conn");
    }
}


function onConnected() {
    // Subscribe to certain Pulic Topic
    stompClient.subscribe("/topic"+subscribedChannel, onMessageReceived);

    // Subscribe to private 
    stompClient.subscribe("/user/"+username+"/private", onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN', channel: subscribedChannel})
    )
    
    // Hide Loading Area
    connectingElement.classList.add('hidden');
    chatHeader.textContent = "✅[Topic]"+subscribedChannel;

    // Load MQ to the chatting window
    MQConsume("publicQueue");
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage() {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            // When public, channel means actual channel;
            // When private, channel means message receiver.
            channel: subscribedChannel
        };
        console.log("channel:"+ chatMessage.channel);
        if (receiver !== null) {
            chatMessage.channel = receiver;
            console.log("Send private message to receiver: "+chatMessage.channel);
            stompClient.send("/app/chat.sendPrivateMessage", {}, JSON.stringify(chatMessage));
            // Show self-send message on chatting window
            appendUserMessage(chatMessage.content, chatMessage.sender);
            // Enqueue a message to MQ: receiver, channel means receiver when chatting private
            MQEnqueue(chatMessage, chatMessage.channel);
            
        } else {
            console.log("Send public message through channel: "+chatMessage.channel);
            stompClient.send("/app/chat.sendPublicMessage", {}, JSON.stringify(chatMessage));
        }
        messageInput.value = '';
    }
}

function switchPublicAndPrivate(element){
    var messageElement = document.createElement("li");
    if(element.classList.contains('selected')){
        // Switch to public broadcast
        receiver = null;
        element.classList.remove("selected");
        cleanAllChild(messageArea);
        // Add a system message.
        appendSystemMessage("--Public Broadcasting--");
        // Load MQ
        MQConsume("publicQueue");
        messageElement.classList.add('event-message');
    } else {
        // Clear all selected class
        document.querySelectorAll('#online-list li').forEach(element => {
            element.classList.remove('selected');
        });
        // Switch to C2C chat
        var name = element.getAttribute("name");
        receiver = name;
        element.classList.add("selected");
        cleanAllChild(messageArea);
        // Add a system message.
        appendSystemMessage("--Chatting to "+element.getAttribute("name")+" --");
        // Load MQ
        MQConsume(receiver);
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    console.log(message);
    var messageElement = document.createElement('li');
    
    if(message.type === 'JOIN') {
        
        updateUserList(message.content);

        // BroadCast: xxx joined!
        if(receiver == null) {
            // Public room notification only.
            appendSystemMessage(message.sender + ' joined!');
        }
    } else if (message.type === 'LEAVE') {
        //delete a user
        var _ = onlineList.querySelector('li[name='+message.sender+']');
        _.remove();

        // Delete message record from MQ
        MQDeleteChannel(message.sender);

        // Add messages for other subscribers
        if(receiver == null) {
            // Public room notification only.
            appendSystemMessage(message.sender + ' left!');
        }
    } else {
        // CHAT Message
        // EnQueue a message
        if(message.channel.startsWith("/")) {
            // Is a public message
            MQEnqueue(message, "publicQueue");
            // Display Message if currently receiving public message (receiver = null)
            if(receiver == null){
                appendUserMessage(message.content, message.sender);    
            }
            
        } else {
            MQEnqueue(message, message.sender);
            // Display Message if currently receiving sender's message (reciever = sender)
            if(receiver == message.sender) {
                appendUserMessage(message.content, message.sender);
            }
        }
        console.log(MQ);
        // End
        
    }
}

function MQConsume(channel) {
    console.log("Loading channel: "+channel);
    // Try to access a channel, init if undefined
    if (MQ[channel] == undefined) {
        MQ[channel] = [];
    }
    console.log(MQ);
    var len = MQ[channel].length;
    // FIFO Traverse
    for(var i=0; i<len; i++){
        appendUserMessage(MQ[channel][i].content, MQ[channel][i].sender);
    }
}

function MQDeleteChannel(channel) {
    console.log("Deleting channel: "+channel);
    delete MQ[channel];
}

function MQEnqueue(message, channel) {
    console.log("Pushing message to channel: "+channel);
    // Try to access a channel, init it if undefined
    if (MQ[channel] == undefined) {
        MQ[channel] = [];
    }
    // Get rid of the first message when the queue overflows
    if(MQ[channel].length >= MAX_MESSAGE_IN_QUEUE) {
        MQ[channel].shift();
    }
    MQ[channel].push(message);
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function quitChatting() {
    stompClient.disconnect(function () {
        chatPage.classList.add('hidden');
        alert("See you next time!");
        usernamePage.classList.remove('hidden');
    });

    cleanAllChild(messageArea);
}

function cleanAllChild(element){
    // Clean Up Message Area
    var children = element.childNodes;
    for(var i=children.length-1; i>=0; i--){
        children[i].remove();
    }
}

function getCurrentTime(){
    var date = new Date()
    return date.getMonth()+'/'+date.getDate()+'  '+date.getHours()+':'+date.getMinutes();
}

function updateUserList(message) {
    // Update user list
    var children = onlineList.childNodes;
    var tmp;
    for(var i=children.length-1; i>=0; i--){
        children[i].remove();
    }
    message.slice(1, message.length-1).split(',').forEach(element => {
        tmp = document.createElement('li');
        tmp.appendChild(document.createTextNode(element.trim()));
        tmp.setAttribute("name", element.trim());
        tmp.setAttribute("onclick", `switchPublicAndPrivate(this);`);
        onlineList.appendChild(tmp);
    });
}

function appendSystemMessage(message) {
    var messageElement = document.createElement('li');
    messageElement.classList.add('event-message');
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function appendUserMessage(message, sender) {
    var messageElement = document.createElement('li');
    messageElement.classList.add('chat-message');

    var avatarElement = document.createElement('i');
    var avatarText = document.createTextNode(sender.charAt(0));
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(sender);

    messageElement.appendChild(avatarElement);

    var usernameElement = document.createElement('span');
    var usernameText = document.createTextNode(sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
    var anotherSpan = document.createElement('span');
    anotherSpan.setAttribute("style", "float: right; font-weight: 100;");
    anotherSpan.appendChild(document.createTextNode(getCurrentTime()));
    messageElement.appendChild(anotherSpan);

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}