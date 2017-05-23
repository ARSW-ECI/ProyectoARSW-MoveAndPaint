/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.controllers.MoveAndPaintRESTController;
import com.escuelaing.arsw.msgbroker.model.Ganador;
import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.model.Room;
import com.escuelaing.arsw.msgbroker.security.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
@Service
public class MoveAndPaintRoomServicesStub implements MoveAndPaintRoomServices {

    ConcurrentHashMap<Integer, Room> roomGame;
    String[] posiciones = {"170 250 Amarillo", "930 250 Fantasma", "170 250 Rojo", "930 250 Morado", "170 390 Verde", "930 390 Azul", "170 390 Naranja", "930 390 Sasuke"};

    @Autowired
    MoveAndPaintRegisterServices participants;

    public String[] getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(String[] posiciones) {
        this.posiciones = posiciones;
    }

    public MoveAndPaintRoomServicesStub() {
        roomGame = new ConcurrentHashMap<>();
        for (int i = 1; i < 21; i++) {
            roomGame.put(i, new Room());
        }
    }

    @Override
    public boolean registerPlayerRoom(int idRoom, Jugador player) throws ServicesException{
        synchronized (roomGame.get(idRoom)) {
            String passVerdadera = participants.getPlayerRegistered(player.getName()).getPass();
            String sal = participants.getPlayerRegistered(player.getName()).getSalt();
            String passNueva = player.getPass();
            HashSalt hs;
            boolean isValid = false;
            try {
                hs = PasswordUtil.getHash(player.getPass() + sal);
                isValid = PasswordUtil.ValidatePass(passNueva, passVerdadera, sal);
            } catch (Exception ex) {
                Logger.getLogger(MoveAndPaintRoomServicesStub.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (roomGame.get(idRoom).getPlayers().size() < MAX_PLAYERS) {
                if (!isValid) {
                    Exception e = new ServicesException("Incorrect password");
                    Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, e);
                } else if (roomGame.get(idRoom).getPlayers().contains(player) || player.getIsPlaying()) {
                    Exception e = new ServicesException("Player " + player.getName() + " already registered in room " + idRoom);
                    Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, e);
                } else {
                    player.setPosX(Integer.parseInt(posiciones[roomGame.get(idRoom).getActual() % 7].split(" ")[0]));
                    player.setPosY(Integer.parseInt(posiciones[roomGame.get(idRoom).getActual() % 7].split(" ")[1]));
                    player.setColor(posiciones[roomGame.get(idRoom).getActual() % 7].split(" ")[2]);
                    roomGame.get(idRoom).setActual(roomGame.get(idRoom).getActual() + 1);
                    player.setPass(passVerdadera);
                    player.setSalt(sal);
                    participants.changeGameState(player, true);
                    roomGame.get(idRoom).getPlayers().add(player);
                    if (roomGame.get(idRoom).getPlayers().size() == MAX_PLAYERS) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public Jugador getJugador(int idRoom, String username) throws ServicesException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MoveAndPaintRoomServicesStub.class.getName()).log(Level.SEVERE, null, ex);
        }
        synchronized (roomGame.get(idRoom)) {
            for (Jugador player : roomGame.get(idRoom).getPlayers()) {
                if (player.getName().equals(username)) {
                    return player;
                }
            }
            Exception e = new ServicesException("Room #" + idRoom + " doesnt contain player");
            Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }
    
    @Override
    public void resetPlayers(int room){
        for (Jugador player : roomGame.get(room).getPlayers()) {
            participants.changeGameState(player, false);
        }
    }

    @Override
    public void cleanRoom(int room) {
        resetPlayers(room);
        roomGame.get(room).resetRoom();
    }

    @Override
    public Set<Jugador> getPlayersinRoom(int idRoom) {
        if (!roomGame.containsKey(idRoom)) {
            roomGame.put(idRoom, new Room());
        }
        return roomGame.get(idRoom).getPlayers();
    }

    @Override
    public String getWinner(int room, Ganador gan) {
        return roomGame.get(room).resultGame(gan);
    }

}
