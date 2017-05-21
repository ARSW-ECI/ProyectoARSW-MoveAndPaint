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
public interface MoveAndPaintRegisterServices {
    
    public void registerPlayer(Jugador jugadorMovePaint) throws ServicesException;
    public Set<Jugador> getPlayersRegistered() throws ServicesException;
    public Jugador getPlayerRegistered(String username)throws ServicesException;
    public void addScorePlayer(String name,int score)throws ServicesException;
}
