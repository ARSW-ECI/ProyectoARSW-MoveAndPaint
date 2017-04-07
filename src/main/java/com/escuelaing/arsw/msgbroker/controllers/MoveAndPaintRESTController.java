/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.controllers;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintServices;
import com.escuelaing.arsw.msgbroker.services.ServicesException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Carlos
 */
@RestController
@RequestMapping(value = "/games")
public class MoveAndPaintRESTController {
    
    @Autowired
    MoveAndPaintServices services;
    
    
    
    @RequestMapping(path = "/participants",method = RequestMethod.GET)
    public ResponseEntity<?> getRaceParticipantsNums() {
        
        try {
            return new ResponseEntity<>(services.getRegisteredPlayers(),HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
        } catch (NumberFormatException ex){
            Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("/{numeroSala}/ must be an integer value.",HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
    
        @RequestMapping(value = "/{numeroSala}/participants", method = RequestMethod.PUT)
    public ResponseEntity<?> addParticipantNum(@PathVariable(name = "numeroSala") String numeroSala, @RequestBody Jugador user) {
            System.out.println("APIREST"+user.getColor());
        try {
            //registrar dato
            services.registerPlayerToGame(Integer.parseInt(numeroSala), user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.FORBIDDEN);
        }
    }
    

}
