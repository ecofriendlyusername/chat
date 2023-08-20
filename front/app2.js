const channels = [];
document.getElementById("myButton").addEventListener("click", connect, true)

var name = null;
async function connect(event) {
    // name = document.querySelector('#name').value.trim();
    // if (name) {
    //     document.querySelector('#welcome-page').classList.add('hidden');
    //     document.querySelector('#dialogue-page').classList.remove('hidden');
    //     stompClient.activate();
    // }
    await fetchData();
    // event.preventDefault();
}

// document.addEventListener("DOMContentLoaded", () => {
    
//     stompClient.activate();
// });

const fetchData = async () => {
    try {
        const response = await fetch("http://localhost:8081/mainchannel/getmainchannel", {
            method: 'GET',
            credentials: 'include'
         })
        //  const response = await fetch("http://localhost:8081/chatrooms/alluserchatrooms", {
        //     method: "GET", // *GET, POST, PUT, DELETE, etc.
        //     // mode: "cors", // no-cors, *cors, same-origin
        //     credentials: "include", // include, *same-origin, omit
        //     headers: {
        //       "Content-Type": "application/json",
        //     }
        //   });
          print(response)
         const data = await response.json() // await!!
         channels.push(data[0])
         console.log(channels.length + ' !!')
         console.log(data[0], channels[0], ' questionable')
         stompClient.activate();
        //  .error(error => {
        //     console.log(error)
        //     return error
        //  }); 
         // Replace with your backend API URL
       //  const data = await response.json();
        // const dataDiv = document.querySelector("#data");
        // dataDiv.textContent = JSON.stringify(data, null, 2);
        // console.log(data[0]['destination'])
        // console.log(data[0]['memberEmails'][0])
        // console.log(data[0]['id'])
        return response
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
    console.log(channels.length)
    channels.forEach((channel) => {
        destination = channel['destination']

        var inputElement = document.createElement("input");
        inputElement.type = "text";
        inputElement.placeholder = "Enter text";
        inputElement.id = destination + '-input'

        // Create the <button> element
        var buttonElement = document.createElement("button");
        buttonElement.id = destination + '-button'
        buttonElement.textContent = "Click me";

        // Create the <div> element

        const textBox = document.createElement("div");
        textBox.id = destination + '-textBox';

        const channelDiv = document.createElement("div");
        channelDiv.id = destination

        channelDiv.appendChild(inputElement);
        channelDiv.appendChild(buttonElement);
        channelDiv.appendChild(textBox);

        document.querySelector("#channels").appendChild(channelDiv);

        document.getElementById(buttonElement.id).addEventListener('click', function() {
            sendMessage(inputElement.id, destination)
        })
        

        stompClient.subscribe('/topic/'+destination, (message) => {
            const messageBody = JSON.parse(message.body); // Assuming the message is in JSON format
            const realMessage = console.log(messageBody['content'])
            displayMessage(textBox.id, messageBody['content']);
        });
    });
    // stompClient.subscribe('/topic/x', onMessageReceived);
    // stompClient.publish({
    //     destination: "/app/chat.newUser",
    //     body: JSON.stringify({sender: name, type: 'newUser'})
    // });
};

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

const displayMessage = (textBoxId, message) => {
    const textBox = document.getElementById(textBoxId);
    const messageDiv = document.createElement("div");
    messageDiv.textContent = message;
    textBox.appendChild(messageDiv);
};

// stompClient.connect({}, () => {
//     channels.forEach(channel => {
//         const channelDiv = document.createElement("div");
//         channelDiv.id = channel;
//         document.querySelector("#channels").appendChild(channelDiv);
        
//         stompClient.subscribe('/topic/'+channel['destination'], message => {
//             const messageBody = JSON.parse(message.body); // Assuming the message is in JSON format
//             displayMessage(channel, messageBody.message);
//         });
//     });
// });
