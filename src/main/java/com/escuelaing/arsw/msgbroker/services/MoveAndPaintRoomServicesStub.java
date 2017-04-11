/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
@Service
public class MoveAndPaintRoomServicesStub implements MoveAndPaintRoomServices {

    ConcurrentHashMap<Integer, Set<Jugador>> roomGame = new ConcurrentHashMap<>();

    public MoveAndPaintRoomServicesStub() {
        roomGame.put(1, new ConcurrentSkipListSet<>());
    }

    @Override
    public boolean registerPlayerRoom(int idRoom, Jugador player) throws ServicesException {
        System.out.println("Entro1");
        if (!roomGame.containsKey(idRoom)) {
            throw new ServicesException("Room " + idRoom + " not registered in the server.");
        } else {
            if (roomGame.get(idRoom).size()<4) {
                if (roomGame.get(idRoom).contains(player)) {
                    throw new ServicesException("Player " + player.getName() + " already registered in room " + idRoom);
                } else {
                    roomGame.get(idRoom).add(player);
                    if(roomGame.get(idRoom).size()==4){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
