const baseurl = "http://localhost:8081"


var chatrooms = [];
var mainchannel = null;

document.getElementById("myButton").addEventListener("click", connect, true)
document.getElementById("invitationButton").addEventListener("click", makeAChatRoom, true)
document.getElementById("friend-request-button").addEventListener("click", makeAFriendRequest, true)

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
        await fetch("http://localhost:8081/mainchannel/getmainchannel", {
            method: 'GET',
            credentials: 'include'
        })
        .then(response => response.text())
        .then(data => {
            mainchannel = data;
        })
        .catch(error => {
            console.error('Error fetching string:', error);
        });
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
    chatrooms.forEach((chatroom) => {
        const destination = chatroom['destination']
        console.log(destination)
        openNewChatRoom(chatroom)
    });
    if (mainchannel === null) {
        console.log('main channel is missing!!!!!')
    } else {
        var destination = mainchannel
        console.log(destination)
        // 메인 채널에 subscribe한다.
        stompClient.subscribe('/topic/'+destination, (message) => {
            const messageBody = JSON.parse(message.body); // Assuming the message is in JSON format
            const type = messageBody['type']
            // 메인 채널에서 온 메시지의 타입이 INVITATION이라면 채팅방에 들어간다(채팅방 박스가 생김)
            if (type === 'INVITATION') {
                console.log('received invitation!')
                const chatroom = {
                    'id': messageBody['chatRoomId'],
                    'destination': messageBody['destination'],
                    'roomName' : null,
                    'memberEmails' : null
                }
                chatrooms.push(chatroom);
                openNewChatRoom(chatroom);
            } else if (type === 'FRIEND_REQUEST') {
                processFriendRequest(messageBody)
            } else {
                console.log('this is not an invitation!!!')
            }
        });
    }
};

function makeAFriendRequest() {
    const to = document.getElementById("friend-request").value;
    
    const friendRequest = {
        'strangerEmail' : to,
        'helloMessage': '친구 요청할 때 메시지.. 뭐 테스트용으로 암거나'
    }

    fetch(baseurl + "/friends/friendrequest", {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(friendRequest)
    })
    .then(function (data) {
        console.log('Request succeeded with JSON response', data);
    })
    .catch(function (error) {
        console.log('Request failed', error);
    });
}

function processFriendRequest(friendRequest) {
    // 여기선 그냥 자동으로 승낙하게 만들어둠 
    // 프론트에서 누구한테 왔는지 (friendRequest['from']), 인삿말 (friendRequest['helloMessage']) 유저한테 보내고 
    const from = friendRequest['from']
    const helloMessage = friendRequest['helloMessage']
    const friendRequestId = friendRequest['friendRequestId']

    console.log(from)
    console.log(helloMessage)

    const responseToFriendRequestDto = {
        friendRequestId,
        'accept': true
    }

    fetch(baseurl + "/friends/handleresponsetofriendrequest", {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(responseToFriendRequestDto)
    })
    .then(function (data) {
        console.log('Request succeeded with JSON response', data);
    })
    .catch(function (error) {
        console.log('Request failed', error);
    });

}

function inviteUsers(inputBoxId1, inputBoxId2, chatroom) {
    const invitation1 = document.getElementById(inputBoxId1).value;
    const invitation2 = document.getElementById(inputBoxId2).value;

    const destination = chatroom['destination']

    var invitees = []

    if (invitation1.length >= 3) invitees.push(invitation1);
    if (invitation2.length >= 3) invitees.push(invitation2);

    // roomName;invitees;
    const chatRoomInvitation = {
        'chatRoomId': chatroom['id'],
        'invitees' : invitees,
        'destination' : chatroom['destination']
    }

    fetch(baseurl + "/chatrooms/invite/" + destination, {
        method: 'post',
        credentials: 'include',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(chatRoomInvitation)
    })
    .then(function (data) {
        console.log('Request succeeded with JSON response', data);
    })
    .catch(function (error) {
        console.log('Request failed', error);
    });
}

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

function openNewChatRoom(chatroom) { // open a new chat room (subscribe & receive messages)
    const destination = chatroom['destination']
    console.log('opening new chat room')
    var inputElement = document.createElement("input");
    inputElement.type = "text";
    inputElement.placeholder = "Enter text";
    inputElement.id = destination + '-input'
    // 일반 텍스트 채팅이 들어갈 인풋박스(사용자가 키보드로 입력할)

    var fileInputElement = document.createElement("input");
    fileInputElement.type = "file";
    fileInputElement.id = destination + '-file-input'
    // 파일 업로드를 위한 엘레먼트

    var buttonElement = document.createElement("button");
    buttonElement.id = destination + '-button'
    buttonElement.textContent = "채팅 보내기";
    // 채팅 보내는 버튼 

    const textBox = document.createElement("div");
    textBox.id = destination + '-textBox';
    // 채팅 내용이 들어갈 박스 

    const showInputButton = document.createElement("button");
    showInputButton.id = "showInputButton";
    showInputButton.textContent = "초대할 유저들 이메일 입력하기";

    const inviteeContainer = document.createElement("div");
    inviteeContainer.id = "inputContainer";
    inviteeContainer.style.display = "none";

    const inviteeInput1 = document.createElement("input");
    inviteeInput1.type = "text";
    inviteeInput1.id = destination + '-invitee-input'
    inviteeInput1.placeholder = "invitee's email";
    inviteeContainer.appendChild(inviteeInput1);

    const inviteeInput2 = document.createElement("input");
    inviteeInput2.type = "text";
    inviteeInput2.id = destination + '-invitee-input2'
    inviteeInput2.placeholder = "invitee's email";
    inviteeContainer.appendChild(inviteeInput2);

    const channelDiv = document.createElement("div");
    channelDiv.id = destination

    channelDiv.appendChild(inviteeContainer);
    channelDiv.appendChild(inputElement);
    channelDiv.appendChild(buttonElement);
    channelDiv.appendChild(fileInputElement);
    channelDiv.appendChild(showInputButton);
    channelDiv.appendChild(textBox);

    document.querySelector("#chatrooms").appendChild(channelDiv);

    showInputButton.addEventListener("click", () => {
        if (inputContainer.style.display === "none") {
            inputContainer.style.display = "block";
            showInputButton.textContent = "유저 초대하기";
        } else {
            inviteUsers(inviteeInput1.id, inviteeInput2.id, chatroom);
            inputContainer.style.display = "none";
            showInputButton.textContent = "초대할 유저들 이메일 입력하기";
        }
    });

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