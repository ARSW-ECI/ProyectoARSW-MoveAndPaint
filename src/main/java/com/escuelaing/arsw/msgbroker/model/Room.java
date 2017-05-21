package com.escuelaing.arsw.msgbroker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
public class Room {

    private Set<Jugador> players;
    private Object o = new Object();
    private List<Ganador> ganadores;
    private String ganador = "";
    private int actual = 0;

    public Room() {
        players = new ConcurrentSkipListSet<>();
        ganadores = new ArrayList<>();
    }

    public String getGanador() {
        return ganador;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public Set<Jugador> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Jugador> players) {
        this.players = players;
    }

    public List<Ganador> getGanadores() {
        return ganadores;
    }

    public void resetRoom() {
        synchronized (o) {
            this.players.clear();
            this.ganadores.clear();
            this.ganador = "";
        }
    }

    public String resultGame(Ganador gan) {
        synchronized (o) {
            ganadores.add(gan);
            if (ganadores.size() == 2) {
                int premio = -1;
                for (int i = 0; i < ganadores.size(); i++) {
                    if (premio == ganadores.get(i).getScore()) {
                        ganador = "Hubo empate en la partida!!!";
                    } else if (premio < ganadores.get(i).getScore()) {
                        premio = ganadores.get(i).getScore();
                        ganador = "El ganador fue: " + ganadores.get(i).getName() + " con: " + ganadores.get(i).getScore() + " numero de plataformas pintadas!!!";
                    }
                }
            }
            return ganador;
        }

    }
}
