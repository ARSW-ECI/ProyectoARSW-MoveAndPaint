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
 * @author Carlos
 */
public interface MoveAndPaintServices {
     public void registerPlayerToGame(int numeroSala, Jugador jugadorMovePaint) throws ServicesException;
     public Set<Jugador> getRegisteredPlayers(int numerosala)throws ServicesException;
    
   
}
