/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.controllers;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRegisterServices;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRoomServicesStub;
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
    MoveAndPaintRegisterServices services;
    
    
    @RequestMapping(path = "/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getAllParticipants() {
        try {
            return new ResponseEntity<>(services.getPlayerRegistered(), HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    
    @RequestMapping(path = "/{username}/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getAllParticipants(@PathVariable String username) {
        try {
            return new ResponseEntity<>(services.getPlayerRegistered(username), HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }  
    
    @RequestMapping(value = "/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipantDataBase(@RequestBody Jugador user) {
        try {
            services.registerPlayer(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            Logger.getLogger(MoveAndPaintRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
