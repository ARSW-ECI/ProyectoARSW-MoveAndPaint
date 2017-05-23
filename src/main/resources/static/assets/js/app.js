var stompClient;
var username;
var password;
var roomid;

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

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
    roomid = document.getElementById("room").value;
    if (username == "" || password == "" || roomid == "") {
        alert("LLENE TODOS LOS CAMPOS!!");
    } else {
        if (roomid >= 1 && roomid <= 20) {
            localStorage.setItem('username', username);
            localStorage.setItem('idRoom', roomid);

            var promise = $.get("/games/" + username + "/participants");

            promise.then(
                    function (data) {

                        stompClient.subscribe('/topic/login.' + roomid, function (data) {
                            window.location = "/game/juego.html";
                        });

                        stompClient.send("/app/" + roomid + "/inRoom", {}, JSON.stringify(data));

                        var getuserpromise = $.get("/otros/participantinroom/" + username + "/" + roomid);

                        getuserpromise.then(
                                function (data) {
                                    alert("EL USUARIO ACABA DE REGISTARSE EN LA SALA O YA ESTA EN COLA!!!");
                                },
                                function () {
                                    alert("EL USUARIO NO PUDO INGRESAR A LA SALA!!!");
                                }
                        );
                    },
                    function () {
                        alert("ERROR AL ENTRAR EN COLA A LA PARTIDA!!");
                    }
            );
        }
        else{
            alert("SALA NO DISPONIBLE");
        }
    }
}

function limpiar(){
    document.getElementById("usuario").value = "";
    document.getElementById("email").value = "";
    document.getElementById("password").value = "";
    document.getElementById("password2").value = "";
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
                "puntajeActual": 0,
                "puntajeAcumulado": 0,
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
                        limpiar();
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
            connect();
        }
);
