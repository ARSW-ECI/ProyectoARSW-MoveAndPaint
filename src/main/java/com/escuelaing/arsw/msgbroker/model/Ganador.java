package com.escuelaing.arsw.msgbroker.model;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
public class Ganador {
    private String name;
    private int score;

    public Ganador(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Ganador() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
