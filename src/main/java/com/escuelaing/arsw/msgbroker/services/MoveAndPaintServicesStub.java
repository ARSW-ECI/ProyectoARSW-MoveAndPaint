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
public class MoveAndPaintServicesStub implements MoveAndPaintServices {

    ConcurrentHashMap<Integer, Set<Jugador>> salas = new ConcurrentHashMap<>();

    public MoveAndPaintServicesStub() {
        salas.put(1, new ConcurrentSkipListSet<>());
    }

    @Override
    public void registerPlayerToGame(int numeroSala, Jugador jugadorMovePaint) throws ServicesException {
        if (!salas.containsKey(numeroSala)) {
            throw new ServicesException("Race " + numeroSala + " not registered in the server.");
        } else {
            if (salas.get(numeroSala).contains(jugadorMovePaint)) {
                throw new ServicesException("Racer " + jugadorMovePaint.getColor() + " already registered in race " + numeroSala);
            } else {
                salas.get(numeroSala).add(jugadorMovePaint);
            }
            

        }
    }
    
    @Override
    public Set<Jugador> getRegisteredPlayers(int numerosala) throws ServicesException {
        return salas.get(numerosala);
    }

}
