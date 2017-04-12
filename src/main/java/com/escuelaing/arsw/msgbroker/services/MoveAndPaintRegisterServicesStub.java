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
 * @author Carlos
 */
@Service
public class MoveAndPaintRegisterServicesStub implements MoveAndPaintRegisterServices {

    
    Set<Jugador> players;

    public MoveAndPaintRegisterServicesStub() {
        players = new ConcurrentSkipListSet<>();
        Jugador j1 = new Jugador(0,0,"image","ricardo","ricardo@mail.com");
        Jugador j2 = new Jugador(10,0,"asd","carlos","carlos@mail.com");
        Jugador j3 = new Jugador(10,10,"qwe","juan","juan@mail.com");
        Jugador j4 = new Jugador(0,10,"zxc","mateo","mateo@mail.com");
   //     Jugador j5 = new Jugador(30,30,"vfr","rodrigo","rodrigo@mail.com");
        players.add(j1);
        players.add(j2);
        players.add(j3);
        players.add(j4);
        
    }

    @Override
    public void registerPlayer(Jugador jugadorMovePaint) throws ServicesException {
        if(players.contains(jugadorMovePaint)){
            throw new ServicesException("Player "+jugadorMovePaint.getName()+" already registered");
        }else{
            players.add(jugadorMovePaint);
        }
    }

    
    
    @Override
    public Set<Jugador> getPlayerRegistered() throws ServicesException {
        if(players.isEmpty()){
            throw new ServicesException("Any player registered!!");
        }
        return players;
    }

    @Override
    public Jugador getPlayerRegistered(String username) throws ServicesException{
        for (Jugador player : players) {
            if(player.getName().equals(username)){
                return player;
            }
        }
        throw new ServicesException("Player not found!");
    }

}
