var stompClient;
var participante;

function Login() {
    var done = 0;
    var username = document.login.username.value;
    username = username.toLowerCase();
    var password = document.login.password.value;
    password = password.toLowerCase();
    if (username == "carlos" && password == "1234") {
        window.location = "juego.html";
        done = 1;
        suscribir();
        window.alert("Ingreso correcto");
    }
    if (done == 0) {
        window.location = "index.html";
        window.alert("Ingreso fallido");
    }
}
function registrar(form) {

    if (form.usuario.value == "") {
        alert("Debe igresar un nombre de usuario");
        //form.usuario.focus();
        return false;
    }
    if (form.password.value == "") {
        alert("Debe igresar una clave");
       // form.password.focus();
        return false;
    }
    if (form.password2.value == "") {
        alert("Debe verificar la contraseña");
        //form.password2.focus();
        return false;
    }
    if (form.password.value != form.password2.value) {
        alert("Las 2 contraseñas no coniciden");
        //form.password.focus();
        return false;
    }
    window.alert("Usuario registrado");
    suscribir();
    return true;


}


$(document).ready(
        function () {
           
        }
);
