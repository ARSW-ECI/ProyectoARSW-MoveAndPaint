/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.controllers;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRegisterServices;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRoomServices;
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
@RequestMapping(value = "/otros")
public class MoveAndPaintRESTRoomController {

    @Autowired
    MoveAndPaintRoomServices services;

    @RequestMapping(path = "/participantsmod", method = RequestMethod.GET)
    public ResponseEntity<?> getAllParticipantsMod() {
        return new ResponseEntity<>(services.getJug(), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/participantsmod/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayer(@PathVariable String username) {
        return new ResponseEntity<>(services.getJugador(username), HttpStatus.ACCEPTED);
    }
}
