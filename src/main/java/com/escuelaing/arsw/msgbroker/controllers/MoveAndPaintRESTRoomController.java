/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.controllers;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.escuelaing.arsw.msgbroker.services.MoveAndPaintRoomServices;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
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

    @RequestMapping(path = "/salas/{idRoom}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoom(@PathVariable int idRoom) {
        return new ResponseEntity<>(services.getRoomGame().get(idRoom), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/{idSala}/colaboradores", method = RequestMethod.POST)
    public ResponseEntity<?> registerInRoom(@PathVariable int idSala, @RequestBody Jugador player) {
        try {
            services.registerPlayerRoom(idSala, player);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error al agregar colaborador a la sala: " + idSala, HttpStatus.NOT_FOUND);
        }
    }
}
