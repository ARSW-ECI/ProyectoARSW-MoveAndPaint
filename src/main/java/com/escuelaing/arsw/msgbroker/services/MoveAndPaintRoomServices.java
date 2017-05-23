/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Ganador;
import com.escuelaing.arsw.msgbroker.model.Jugador;
import java.util.Set;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
public interface MoveAndPaintRoomServices {
    int MAX_PLAYERS = 2;
    
    public boolean registerPlayerRoom(int idRoom, Jugador player) throws ServicesException;
    public Jugador getJugador(int idRoom, String username) throws ServicesException;
    public Set<Jugador> getPlayersinRoom(int idRoom) throws ServicesException;
    public void cleanRoom(int room);
    public void resetPlayers(int room);
    public String getWinner(int room, Ganador gan);
}
