/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import java.util.Set;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
public interface MoveAndPaintRoomServices {
    public boolean registerPlayerRoom(int idRoom, Jugador player) throws ServicesException;
    public Set<Jugador> getJug();
    public Jugador getJugador(String username);
}
