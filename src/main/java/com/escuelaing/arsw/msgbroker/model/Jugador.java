/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.model;

/**
 *
 * @author Carlos
 */
public class Jugador {
    int posX;
    int posY;
    String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public Jugador(int posX, int posY, String color) {
        this.posX = posX;
        this.posY = posY;
        this.color=color;
    }
    public Jugador() {
        
    }


    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    
    
    
}
