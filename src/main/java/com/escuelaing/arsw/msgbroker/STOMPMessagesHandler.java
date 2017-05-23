package com.escuelaing.arsw.msgbroker;

import com.escuelaing.arsw.msgbroker.model.Ganador;
import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRoomServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author FamiliaRamirez
 */
@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    MoveAndPaintRoomServices services;

    static Logger logger = Logger.getLogger(STOMPMessagesHandler.class);

    @MessageMapping("/{idRoom}/inRoom")
    public void postNewPlayerInRoom(@DestinationVariable int idRoom, Jugador player) throws Exception {
        
        boolean registro = services.registerPlayerRoom(idRoom, player);
        if (registro) {
            //asignacion de jugadores posicion, personaje
            msgt.convertAndSend("/topic/login." + idRoom, player);
            controlTime(idRoom);
        }

    }

    public void controlTime(int idRoom) throws Exception {
        Thread.sleep(95000);
        services.resetPlayers(idRoom);
        msgt.convertAndSend("/topic/endGame." + idRoom, idRoom);
    }

    @MessageMapping("/{idRoom}/winnner")
    public void postNewPlayerInRoom(@DestinationVariable int idRoom, Ganador player) throws Exception {
        String win = services.getWinner(idRoom,player);
        if (!win.equals("")) {
            msgt.convertAndSend("/topic/wininroom." + idRoom,win);
            services.cleanRoom(idRoom);
        }
    }

}
