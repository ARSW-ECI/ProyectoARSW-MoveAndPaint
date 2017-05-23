/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 *
 * @author Carlos
 */
//@Service
public class MoveAndPaintRegisterServicesStub implements MoveAndPaintRegisterServices {

    
    Set<Jugador> players;

    public MoveAndPaintRegisterServicesStub() {
        players = new ConcurrentSkipListSet<>();
        Jugador j1 = new Jugador(0,0,"image","ricardo","ricardo@mail.com",false);
        Jugador j2 = new Jugador(10,0,"asd","carlos","carlos@mail.com",false);
        Jugador j3 = new Jugador(10,10,"qwe","juan","juan@mail.com",false);
        Jugador j4 = new Jugador(0,10,"zxc","mateo","mateo@mail.com",false);
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
            MoveAndPaintRegisterServicesMongoDB fjds = new MoveAndPaintRegisterServicesMongoDB();
            //fjds.registerPlayer(jugadorMovePaint);
            fjds.getPlayersRegistered();
        }
    }
    
    @Override
    public Set<Jugador> getPlayersRegistered() throws ServicesException {
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

    @Override
    public void addScorePlayer(String name, int score) throws ServicesException {
        for (Jugador player : players) {
            if(player.getName().equals(name)){
                player.setPuntajeAcumulado(player.getPuntajeAcumulado()+score);
            }
        }
        throw new ServicesException("Player not found!");
    }

    @Override
    public void changeGameState(Jugador j, boolean b) {
        for (Jugador player : players) {
            if(player.getName().equals(j.getName())){
                player.setIsPlaying(b);
            }
        }
    }

}
