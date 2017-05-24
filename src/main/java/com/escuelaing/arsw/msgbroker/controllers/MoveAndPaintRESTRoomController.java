package com.escuelaing.arsw.msgbroker.controllers;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRoomServices;
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
@RequestMapping(value = "/otros")
public class MoveAndPaintRESTRoomController {

    final static Logger logger = Logger.getLogger(MoveAndPaintRESTController.class);
    
    @Autowired
    MoveAndPaintRoomServices services;

    @RequestMapping(path = "/participantinroom/{username}/{idRoom}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayer(@PathVariable String username, @PathVariable int idRoom) {
        try {
            return new ResponseEntity<>(services.getJugador(idRoom, username), HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            logger.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/participantsinroom/{idRoom}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayer(@PathVariable int idRoom) {
        try {
            return new ResponseEntity<>(services.getPlayersinRoom(idRoom), HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            logger.error(ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{idSala}/colaboradores", method = RequestMethod.POST)
    public ResponseEntity<?> registerInRoom(@PathVariable int idSala, @RequestBody Jugador player) {
        try {
            services.registerPlayerRoom(idSala, player);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            logger.error(ex);
            return new ResponseEntity<>("Error al agregar colaborador a la sala: " + idSala, HttpStatus.NOT_FOUND);
        }
    }
}
