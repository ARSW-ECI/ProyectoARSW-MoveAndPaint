/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.security;

/**
 *
 * @author ricar
 */
public class HashSalt {
    private String hash;
    private String salt;
	
    public HashSalt(String hash, String salt) {
	this.hash = hash;
	this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
 
    
    
}
