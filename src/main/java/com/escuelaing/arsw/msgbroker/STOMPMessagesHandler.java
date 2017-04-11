/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRoomServices;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
        if(registro){
            System.out.println("3ntrelnlfsdn");
            //asignacion de jugadores posicion, personaje
            msgt.convertAndSend("/topic/login."+idRoom,player);
        }
    }

}
