const baseurl = "http://localhost:8081"


var chatrooms = [];
var mainchannel = null;

document.getElementById("myButton").addEventListener("click", connect, true)
document.getElementById("invitationButton").addEventListener("click", makeAChatRoom, true)

var name = null;
async function connect(event) {
    await fetchData();
}
const fetchData = async () => {
    try {
        const userchatroomsResponse = await fetch("http://localhost:8081/chatrooms/alluserchatrooms", {
            method: 'GET',
            credentials: 'include'
        })
        const mainchatroomResponse = await fetch("http://localhost:8081/mainchannel/getmainchannel", {
            method: 'GET',
            credentials: 'include'
        })
        const userchatrooms = await userchatroomsResponse.json() // await!!
        if (userchatrooms !== undefined) {
            console.log(userchatrooms)
            userchatrooms.forEach((userChatRoom) => {
                chatrooms.push(userChatRoom)
            })
            console.log(chatrooms.length + ' !!')
            console.log(userchatrooms[0], chatrooms[0], ' questionable')
        } else {
            console.log('defined but.. ?')
        }
        mainchannel = await mainchatroomResponse.json()
        
        stompClient.activate();
    } catch (error) {
        console.error("Error fetching data:", error);
        return error

    }
};

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/websocketApp'
});

stompClient.onConnect = (frame) => {
    // setConnected(true);
    console.log('Connected: ' + frame);
    console.log(chatrooms.length)
    chatrooms.forEach((channel) => {
        const destination = channel['destination']
        console.log(destination)
        openNewChatRoom(destination)
    });
    if (mainchannel === null) {
        console.log('main channel is missing!!!!!')
    } else {
        var destination = mainchannel['destination']
        console.log(destination)
        stompClient.subscribe('/topic/'+destination, (message) => {
            const messageBody = JSON.parse(message.body); // Assuming the message is in JSON format
            const type = messageBody['type']
            if (type === 'INVITATION') {
                console.log('received invitation!')
                const destination = messageBody['destination']
                openNewChatRoom(destination)
            } else {
                console.log('this is not an invitation!!!')
            }
            // displayMessage(textBox.id, messageBody['content']);
        });
    }
};

function makeAChatRoom() {
    const invitation1 = document.getElementById("invitation1").value;
    const invitation2 = document.getElementById("invitation2").value;

    var invitees = []

    if (invitation1.length >= 3) invitees.push(invitation1);
    if (invitation2.length >= 3) invitees.push(invitation2);

    const roomName = document.getElementById("roomName").value;

    // roomName;invitees;
    const roomRequest = {
        'invitees' : invitees,
        'roomName' : roomName
    }

    console.log('roomRequest ',roomRequest)

    fetch(baseurl + "/chatrooms/makechatroom", {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(roomRequest)
    })
    .then(function (data) {
        console.log('Request succeeded with JSON response', data);
    })
    .catch(function (error) {
        console.log('Request failed', error);
    });
}

function sendMessage(inputBoxId, destination) {
    var messageContent = document.getElementById(inputBoxId).value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender : name,
            content : document.getElementById(inputBoxId).value,
            type : 'CHAT'
        };
        stompClient.publish({
            destination: "/app/chat/" + destination,
            body: JSON.stringify(chatMessage)
        });
        document.getElementById(inputBoxId).value = '';
    }
}

async function sendFile(formData, destination, type) {
    fetch(baseurl + '/chatmessages/uploadfile', {
        method: 'POST',
        credentials: 'include',
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        const fileName = data;

        console.log(fileName)
        
        if (stompClient) {
            var chatMessage = {
                content : fileName,
                type : type
        };
        stompClient.publish({
            destination: "/app/chat/" + destination,
            body: JSON.stringify(chatMessage)
        });
    }
    })
    .catch(error => {
        console.error('Error fetching string:', error);
    });

}


const displayMessage = (textBoxId, messageBody) => {
    if (messageBody['type'] === 'IMAGE') {
        const fileName = messageBody['content']
        fetch(baseurl + '/chatmessages/getfile/' + fileName, {
            method: 'get',
            credentials: 'include',
        })
        .then(response => response.blob())
        .then(blob => {
            const reader = new FileReader();
            reader.onload = () => {
                const imgElement = document.createElement('img');
                imgElement.src = reader.result;
                const textBox = document.getElementById(textBoxId);
                textBox.appendChild(imgElement);
            };
            reader.readAsDataURL(blob);
        })
        .catch(error => console.error('Error loading image:', error));
    } else if (messageBody['type'] === 'FILE') {
        const fileName = messageBody['content']
        fetch(baseurl + '/chatmessages/getfile/' + fileName, {
            method: 'get',
            credentials: 'include',
        })
        .then(response => response.blob())
        .then(blob => {
            const downloadButton = document.createElement("button");
            downloadButton.id = fileName + 'downloadbutton'
            downloadButton.textContent = "Download File";

            const textBox = document.getElementById(textBoxId);

            textBox.appendChild(downloadButton);
            
            downloadButton.addEventListener('click', () => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.id = fileName + 'download'
                a.href = url;
                a.style.display = 'none';
                a.download = fileName;
                textBox.appendChild(a);
                a.click();
                textBox.removeChild(a);
                window.URL.revokeObjectURL(url);
            });
        })
        .catch(error => console.error('Error loading image:', error));
    } else {
        const message = messageBody['content']
        console.log('trying to display message!!!!')
        const textBox = document.getElementById(textBoxId);
        const messageDiv = document.createElement("div");
        messageDiv.textContent = message;
        textBox.appendChild(messageDiv);
    }
};

function openNewChatRoom(destination) { // open a new chat room (subscribe & receive messages)
    console.log('opening new chat room')
    var inputElement = document.createElement("input");
    inputElement.type = "text";
    inputElement.placeholder = "Enter text";
    inputElement.id = destination + '-input'

    var fileInputElement = document.createElement("input");
    fileInputElement.type = "file";
    fileInputElement.id = destination + '-file-input'

    var buttonElement = document.createElement("button");
    buttonElement.id = destination + '-button'
    buttonElement.textContent = "Click me";

    const textBox = document.createElement("div");
    textBox.id = destination + '-textBox';

    const channelDiv = document.createElement("div");
    channelDiv.id = destination

    channelDiv.appendChild(inputElement);
    channelDiv.appendChild(buttonElement);
    channelDiv.appendChild(fileInputElement);
    channelDiv.appendChild(textBox);

    document.querySelector("#chatrooms").appendChild(channelDiv);
    document.getElementById(fileInputElement.id).addEventListener("change",function(event) {
        const selectedFile = event.target.files[0];

        const formData = new FormData();
        formData.append('attachment', selectedFile);
        
        if (selectedFile.type.startsWith('image/')) {
            sendFile(formData, destination, 'IMAGE');
        } else {
            sendFile(formData, destination, 'FILE');
        }
    });
    document.getElementById(buttonElement.id).addEventListener("click",function() {
        sendMessage(inputElement.id, destination);
    });

    stompClient.subscribe('/topic/'+destination, (message) => {
        const messageBody = JSON.parse(message.body); // Assuming the message is in JSON format
        console.log(messageBody['content'])
        displayMessage(textBox.id, messageBody);
    });
}