/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Jugador;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
public interface MoveAndPaintRoomServices {
    public boolean registerPlayerRoom(int idRoom, Jugador player) throws ServicesException;
}
