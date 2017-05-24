package com.escuelaing.arsw.msgbroker.controllers;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRegisterServices;
import com.escuelaing.arsw.msgbroker.services.ServicesException;
import org.apache.log4j.Logger;
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
 * @author Carlos Alberto Ramirez Otero
 */
@RestController
@RequestMapping(value = "/games")
public class MoveAndPaintRESTController {

    final static Logger logger = Logger.getLogger(MoveAndPaintRESTController.class);
    
    @Autowired
    MoveAndPaintRegisterServices services;
    
    @RequestMapping(path = "/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getAllParticipants() {
        try {
            return new ResponseEntity<>(services.getPlayersRegistered(), HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            logger.error(ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(path = "/{username}/participants", method = RequestMethod.GET)
    public ResponseEntity<?> getAllParticipants(@PathVariable String username) {
        try {
            Jugador player = services.getPlayerRegistered(username);
            return new ResponseEntity<>(player, HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            logger.error(ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        }
    }  
    
    @RequestMapping(value = "/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipantDataBase(@RequestBody Jugador user) {
        try {
            services.registerPlayer(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            logger.error(ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
}
