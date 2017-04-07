var stompClient;
var participante;

function Login() {
    var done = 0;
    var username = document.getElementById("nombre").value;
    
        $.get("/games/participants", function (data) {
            
            console.log(data);
            
        for (x in data) {
            if (data.color == username) {
                var kk = x;
                alert(kk);
            }
        }
    });
    
    /*
    if (data == "carlos") {
        window.location = "juego.html";
        done = 1;
        suscribir();
        window.alert("Ingreso correcto");
    }
    if (done == 0) {
        window.location = "index.html";
        window.alert("Ingreso fallido");
    }*/
}
function registrar() {
    alert("esta en regstro");

    var nombre = document.getElementById("usuario").value;
    var pass = document.getElementById("password").value;
    if (nombre == "" || pass == "") {
        alert("llene todos los campos");
    } else {
        var jugador = {
            "posX": 0,
            "posY": 0,
            "color": nombre
        };
        
        $.ajax({
            url: "/games/1/participants",
            type: 'PUT',
            data: JSON.stringify(jugador),
            contentType: "application/json"
        });
        alert("Sus datos han sido Registrados   " + nombre + "GRACIAS");
        window.location = "juego.html";
    
    }
}



$(document).ready(
        function () {
           
        }
);
