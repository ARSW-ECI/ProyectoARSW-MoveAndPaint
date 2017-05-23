package com.escuelaing.arsw.msgbroker.model;

/**
 *
 * @author Carlos Alberto Ramirez Otero
 */
public class Jugador implements Comparable<Jugador> {

    private int posX, posY, puntajeAcumulado;
    String color, name, email, pass, salt;
    private boolean isPlaying;

    public Jugador(int posX, int posY, String color, String name, String email, boolean isPlaying, String pass, String salt) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
        this.name = name;
        this.email = email;
        this.isPlaying = isPlaying;
        this.pass = pass;
        this.salt = salt;
    }

    public Jugador() {

    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    @Override
    public int compareTo(Jugador o) {
        if (o.getName().equals(this.name) || o.getEmail().equals(this.email)) {
            return 0;
        }
        return 1;
    }

}
