/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import java.util.LinkedList;
import java.util.List;
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

    List<Jugador> jugadores=new LinkedList<>();

    public MoveAndPaintServicesStub() {
        //salas.put(1, new ConcurrentSkipListSet<Jugador>());
    }

    @Override
    public void registerPlayerToGame(int numeroSala, Jugador jugadorMovePaint) throws ServicesException {
        
        System.out.println(jugadorMovePaint.getColor());

        
                jugadores.add(jugadorMovePaint);
                System.out.println("Numero de valores: "+jugadores.size());
            
            

        
    }
    

    @Override
    public List<Jugador> getRegisteredPlayers() throws ServicesException {
        return jugadores;
    }
 
    


}
