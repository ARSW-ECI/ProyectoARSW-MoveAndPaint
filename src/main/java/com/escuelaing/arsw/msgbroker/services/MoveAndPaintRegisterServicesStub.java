/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.security.HashSalt;
import com.escuelaing.arsw.msgbroker.security.PasswordUtil;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
//@Service
public class MoveAndPaintRegisterServicesStub implements MoveAndPaintRegisterServices {

    Set<Jugador> players;

    public MoveAndPaintRegisterServicesStub() throws Exception {
        players = new ConcurrentSkipListSet<>();
        HashSalt hs = PasswordUtil.getHash("asd");
        String pass = hs.getHash();
        Jugador j1 = new Jugador(0, 0, "image", "ricardo", "ricardo@mail.com",false, pass, hs.getSalt());
        Jugador j2 = new Jugador(10, 0, "asd", "carlos", "carlos@mail.com",false, pass, hs.getSalt());
        Jugador j3 = new Jugador(10, 10, "qwe", "juan", "juan@mail.com",false, pass, hs.getSalt());
        Jugador j4 = new Jugador(0, 10, "zxc", "mateo", "mateo@mail.com",false, pass, hs.getSalt());
        players.add(j1);
        players.add(j2);
        players.add(j3);
        players.add(j4);
    }

    @Override
    public void registerPlayer(Jugador jugadorMovePaint) throws ServicesException {
        if (players.contains(jugadorMovePaint)) {
            throw new ServicesException("Player " + jugadorMovePaint.getName() + " already registered");
        } else {
            try {
                String pass = jugadorMovePaint.getPass();
                HashSalt hs;
                try {
                    hs = PasswordUtil.getHash(pass);
                    jugadorMovePaint.setPass(hs.getHash());
                    jugadorMovePaint.setSalt(hs.getSalt());
                } catch (Exception ex) {
                    Logger.getLogger(MoveAndPaintRoomServicesStub.class.getName()).log(Level.SEVERE, null, ex);
                }
                players.add(jugadorMovePaint);
                MoveAndPaintRegisterServicesMongoDB fjds = new MoveAndPaintRegisterServicesMongoDB();
                //fjds.registerPlayer(jugadorMovePaint);
                fjds.getPlayersRegistered();
            } catch (Exception ex) {
                Logger.getLogger(MoveAndPaintRegisterServicesStub.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Set<Jugador> getPlayersRegistered() throws ServicesException {
        if (players.isEmpty()) {
            throw new ServicesException("Any player registered!!");
        }
        return players;
    }

    @Override
    public Jugador getPlayerRegistered(String username) throws ServicesException {
        for (Jugador player : players) {
            if (player.getName().equals(username)) {
                return player;
            }
        }
        throw new ServicesException("Player not found!");
    }

    @Override
    public void addScorePlayer(String name, int score) throws ServicesException {
        for (Jugador player : players) {
            if (player.getName().equals(name)) {
                player.setPuntajeAcumulado(player.getPuntajeAcumulado() + score);
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
