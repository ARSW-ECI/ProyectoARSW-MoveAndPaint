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
    int actual = 0;
    String[] posiciones = {"170 250 rojo", "170 390 verde", "930 250 morado", "930 390 azul"};

    public MoveAndPaintRoomServicesStub() {
        roomGame.put(1, new ConcurrentSkipListSet<>());
    }

    @Override
    public boolean registerPlayerRoom(int idRoom, Jugador player) throws ServicesException {
        if (!roomGame.containsKey(idRoom)) {
            throw new ServicesException("Room " + idRoom + " not registered in the server.");
        } else {
            if (roomGame.get(idRoom).size() < MAX_PLAYERS) {
                if (roomGame.get(idRoom).contains(player)) {
                    throw new ServicesException("Player " + player.getName() + " already registered in room " + idRoom);
                } else {
                    player.setPosX(Integer.parseInt(posiciones[actual].split(" ")[0]));
                    player.setPosY(Integer.parseInt(posiciones[actual].split(" ")[1]));
                    player.setColor(posiciones[actual].split(" ")[2]);
                    actual++;
                    roomGame.get(idRoom).add(player);
                    if (roomGame.get(idRoom).size() == MAX_PLAYERS) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public Set<Jugador> getJug(){
        System.out.println(roomGame.get(1));
        return roomGame.get(1);
    }
    
    @Override
    public Jugador getJugador(String username){
        Jugador p = null;
        for (Jugador player : roomGame.get(1)) {
            if(player.getName().equals(username)){
                p= player;
            }
        }
        return p;
    }

}
