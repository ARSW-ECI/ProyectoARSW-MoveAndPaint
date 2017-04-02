var stompClient;
var context;
var myGamePiece;
var myObstacle;

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
    myGamePiece = new component(74, 74, "Sasuke1.png", 170, 170, "image");
    myObstacle = new component(70, 40, "green", 250, 175, "rectangle");
    myGameArea.start();
}

var myGameArea = {
    canvas: document.createElement("canvas"),
    start: function () {
        this.canvas.width = 750;
        this.canvas.height = 350;
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
    this.gravity = 0.05;
    this.gravitySpeed = 0;
    this.piso = false;
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
    
    this.crashWith = function(otherobj) {
        var myleft = this.x;
        var myright = this.x + (this.width);
        var mytop = this.y;
        var mybottom = this.y + (this.height);
        var otherleft = otherobj.x;
        var otherright = otherobj.x + (otherobj.width);
        var othertop = otherobj.y;
        var otherbottom = otherobj.y + (otherobj.height);
        var changes = true;
        if ((mybottom < othertop) || (mytop > otherbottom) || (myright < otherleft) || (myleft > otherright)) {
           changes = false;
        }
        return changes;
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
            this.piso = true;
        }
    }
}

function updateGameArea() {
    myGameArea.clear();
    if (myGamePiece.crashWith(myObstacle)) {
        //Verificar si esta por encima, por debajo por el lado derecho o izquiero del obstaculo
        if((myGamePiece.y>myObstacle.y) && (myGamePiece.y<=myObstacle.y+myObstacle.height)){
            myGamePiece.speedY = 0;
        }else if((myGamePiece.y+myGamePiece.height<myObstacle.y+myObstacle.height) && (myGamePiece.y+myGamePiece.height>myObstacle.y)){
            myGamePiece.y = myObstacle.y-myGamePiece.height;
            myGamePiece.gravity = 0;
            myGamePiece.gravitySpeed = 0;
            myGamePiece.piso = true;
        }
    }
    
    myGamePiece.newPos();
    myGamePiece.update();
    myObstacle.update();
    myGamePiece.gravity = 0.05;

}

function move(dir) {
    if (dir == "up" && myGamePiece.piso) {
        for (var i = 0; i < 10; i++) {
            myGamePiece.speedY = -4;
        }
        myGamePiece.gravitySpeed = 0;
        myGamePiece.piso = false;
    }
    if (dir == "down") {
        myGamePiece.speedY = 2;
    }
    if (dir == "left") {
        myGamePiece.image.src = "Sasuke3.png";
        myGamePiece.speedX = -5;
    }
    if (dir == "right") {
        myGamePiece.image.src = "Sasuke2.png";
        myGamePiece.speedX = 5;
    }
}

function clearmove() {
    myGamePiece.image.src = "Sasuke1.png";
    myGamePiece.speedX = 0;
    myGamePiece.speedY = 0;
}

$(document).ready(
        function () {
            document.addEventListener('keydown', function (event) {
                keyCode = event.keyCode;
                if (keyCode == 39) {
                    move('right')
                }
                if (keyCode == 37) {
                    move('left')
                }
                if (keyCode == 38) {
                    move('up')
                }
            }, false);
            document.addEventListener('keyup', function (event) {
                clearmove();
            }, false);
            startGame();
        }
);
