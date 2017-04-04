var stompClient;
var context;
var myGamePiece;
var obstacles = [];
var direccion = null;
var color = null;
var coloresJugadores = null;

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
    setConnected(false);
    console.log("Disconnected");
}

function startGame() {
    myGamePiece = new component(74, 74, color + direccion + ".png", 170, 170, "image");
    for (var i = 0; i < 5; i++) {
        obstacles.push(new component(50, 50, "green", i * 65, 545, "rectangle"));
    }

    myGameArea.start();
}

var myGameArea = {
    canvas: document.createElement("canvas"),
    start: function () {
        this.canvas.width = 1024;
        this.canvas.height = 600;
        this.context = this.canvas.getContext("2d");
        document.body.insertBefore(this.canvas, document.body.childNodes[0]);
        this.interval = setInterval(updateGameArea, 20);
    },
    stop: function () {
        clearInterval(this.interval);
    },
    clear: function () {
        this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }
}

function component(width, height, color, x, y, type) {
    this.type = type;
    if (type == "image") {
        this.image = new Image();
        this.image.src = color;
    }
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.speedX = 0;
    this.speedY = 0;
    this.grounded = false; //inicia flase si el jugador esta en el aire sino true
    this.gravity = 0.05;
    this.gravitySpeed = 0;
    this.friction = 0.8;
    this.jumping = true; //inicia saltando valor true sino false
    this.update = function () {
        ctx = myGameArea.context;
        if (type == "image") {
            ctx.drawImage(this.image,
                    this.x,
                    this.y,
                    this.width, this.height);
        } else {
            ctx.fillStyle = color;
            ctx.fillRect(this.x, this.y, this.width, this.height);
        }
    }

    this.newPos = function () {
        this.gravitySpeed += this.gravity;
        this.x += this.speedX;
        this.y += this.speedY + this.gravitySpeed;
        this.hitBottom();
    }

    this.hitBottom = function () {
        var rockbottom = myGameArea.canvas.height - this.height;
        if (this.y > rockbottom) {
            this.y = rockbottom;
            this.gravitySpeed = 0;
            this.jumping = false;
            this.grounded = true;
        }
    }
}

function updateGameArea() {
    myGameArea.clear();
    myGamePiece.newPos();
    myGamePiece.update();
    for (var i = 0; i < obstacles.length; i++) {
        obstacles[i].update();
        var dir = colCheck(myGamePiece, obstacles[i]);

        if (dir === "l" || dir === "r") {
            myGamePiece.speedX = 0;
            myGamePiece.jumping = false;
        } else if (dir === "b") {
            myGamePiece.grounded = true;
            myGamePiece.jumping = false;
        } else if (dir === "t") {
            myGamePiece.speedY *= -1;
        }

    }

    if (myGamePiece.grounded) {
        myGamePiece.speedY = 0;
        myGamePiece.gravitySpeed = 0;
    }
}

function colCheck(shapeA, shapeB) {
    // get the vectors to check against
    var vX = (shapeA.x + (shapeA.width / 2)) - (shapeB.x + (shapeB.width / 2)),
            vY = (shapeA.y + (shapeA.height / 2)) - (shapeB.y + (shapeB.height / 2)),
            // add the half widths and half heights of the objects
            hWidths = (shapeA.width / 2) + (shapeB.width / 2),
            hHeights = (shapeA.height / 2) + (shapeB.height / 2),
            colDir = null;

    // if the x and y vector are less than the half width or half height, they we must be inside the object, causing a collision
    if (Math.abs(vX) < hWidths && Math.abs(vY) < hHeights) {
        // figures out on which side we are colliding (top, bottom, left, or right)
        var oX = hWidths - Math.abs(vX),
                oY = hHeights - Math.abs(vY);
        if (oX >= oY) {
            if (vY > 0) {
                colDir = "t";
                shapeA.y += oY;
            } else {
                colDir = "b";
                shapeA.y -= oY;
            }
        } else {
            if (vX > 0) {
                colDir = "l";
                shapeA.x += oX;
            } else {
                colDir = "r";
                shapeA.x -= oX;
            }
        }
    }
    return colDir;
}


function move(dir) {
    if (dir == "up" && !myGamePiece.jumping && myGamePiece.grounded) {
        myGamePiece.jumping = true;
        myGamePiece.grounded = false;
        myGamePiece.speedY = -4;
    }
    if (dir == "left") {
        direccion = "Left";
        myGamePiece.image.src = color + direccion + ".png";
        myGamePiece.speedX = -5;
    }
    if (dir == "right") {
        direccion = "Right";
        myGamePiece.image.src = color + direccion + ".png";
        myGamePiece.speedX = 5;
    }
}

function clearmove() {
    myGamePiece.image.src = color + direccion + ".png";
    myGamePiece.speedX = 0;
    myGamePiece.speedY = 0;
}

function suscribir() {
    direcciones = ["Right", "Left"];
    var randomDireccion = Math.floor((Math.random() * 2) + 0);
    direccion = direcciones[randomDireccion];
    coloresJugadores = ["rojo", "azul", "amarillo", "verde", "fantasma", "morado", "naranja"];
    var coloresDisponibles = coloresJugadores.length;
    var randomcolor = Math.floor((Math.random() * coloresDisponibles) + 0);
    color = coloresJugadores[randomcolor];
    coloresJugadores.splice(randomcolor);
    $("#estilo").append("canvas {\n\
    height: 50%;\n\
    width: 50%;\n\
    border:1px solid #d3d3d3;\n\
    background-color: #f1f1f1;}");
    $("#formulario").remove();
    document.addEventListener('keydown', function (event) {
        keyCode = event.keyCode;
        if (keyCode == 39) {
            move('right')
        }
        if (keyCode == 37) {
            move('left')
        }
        if (keyCode == 38 || keyCode == 32) {
            move('up')
        }
    }, false);
    document.addEventListener('keyup', function (event) {
        clearmove();
    }, false);
    startGame();
}

$(document).ready(
        function () {

        }
);
