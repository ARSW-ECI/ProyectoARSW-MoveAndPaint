var stompClient;
var username;
var password;
var roomid;

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/login.' + roomid, function (data) {
            window.location = "juego.html";
        });
    });
}

function disconnect() {

    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function Login() {
    username = document.getElementById("nombre").value;
    password = document.getElementById("passwordlogin").value;


    if (username == "" || password == "" || roomid == "") {
        alert("LLENE TODOS LOS CAMPOS!!");
    } else {
        localStorage.setItem('username', username);
        $.get("/games/" + username + "/participants", function (data) {
            if (data != null) {
                stompClient.send("/app/" + roomid + "/inRoom", {}, JSON.stringify(data));
                $.get("/otros/participantsmod/" + username, function (data) {
                    if (data != null) {
                        alert("HA SIDO REGISTRADO EN LA SALA #" + roomid);
                    }
                    
                });
            }
        });
    }
}

function registrar() {
    var nombre = document.getElementById("usuario").value;
    var email = document.getElementById("email").value;
    var pass = document.getElementById("password").value;
    var pass2 = document.getElementById("password2").value;

    if (nombre == "" || pass == "" || pass2 == "") {
        alert("LLENE TODOS LOS CAMPOS!!");
    } else {
        if (pass != pass2) {
            alert("LAS CLAVES DE REGISTRO DEBEN SER IGUALES");
        } else {
            var jugador = {
                "name": nombre,
                "posX": 0,
                "posY": 0,
                "color": "",
                "email": email
            };

            var postPromise = $.ajax({
                url: "/games/participants",
                type: 'POST',
                data: JSON.stringify(jugador),
                contentType: "application/json"
            });

            postPromise.then(
                    function () {
                        console.info("OK");
                        alert("SUS DATOS HAN SIDO REGISTRADOS CORRECTAMENTE!!");

                    },
                    function () {
                        console.info("ERROR");
                        alert("HUBO UN ERROR AL REGISTRAR EL JUGADOR, DATOS ERRONEOS O USUARIO EXISTENTE!!");
                    }
            );
        }
    }
}


$(document).ready(
        function () {
            roomid = document.getElementById("room").value;
            connect();
        }
);
