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

    ConcurrentHashMap<Integer, Set<Jugador>> roomGame;
    int actual = 0;
    String[] posiciones = {"170 250 Rojo", "930 250 Morado", "170 390 Verde", "930 390 Azul", "170 250 Amarillo", "930 250 Fantasma", "170 390 Naranja", "930 390 Sasuke"};

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String[] getPosiciones() {
        return posiciones;
    }

   
    
    public void setPosiciones(String[] posiciones) {
        this.posiciones = posiciones;
    }

    public MoveAndPaintRoomServicesStub() {
        roomGame = new ConcurrentHashMap<>();
        roomGame.put(1, new ConcurrentSkipListSet<>());
    }

    @Override
    public boolean registerPlayerRoom(int idRoom, Jugador player) throws ServicesException {
        if(actual==7) actual=0;
        if (!roomGame.containsKey(idRoom)) {
            roomGame.put(idRoom, new ConcurrentSkipListSet<>());
            //throw new ServicesException("Room " + idRoom + " not registered in the server.");
        }
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
        return false;
    }

    @Override
    public ConcurrentHashMap<Integer, Set<Jugador>> getRoomGame() {
        return roomGame;
    }

    @Override
    public Set<Jugador> getJug() {
        System.out.println(roomGame.get(1));
        return roomGame.get(1);
    }

    @Override
    public Jugador getJugador(String username) {
        Jugador p = null;
        for (Jugador player : roomGame.get(1)) {
            if (player.getName().equals(username)) {
                p = player;
            }
        }
        return p;
    }

    @Override
    public void cleanRoom(int room) {
        roomGame.get(room).clear();
    }

}
