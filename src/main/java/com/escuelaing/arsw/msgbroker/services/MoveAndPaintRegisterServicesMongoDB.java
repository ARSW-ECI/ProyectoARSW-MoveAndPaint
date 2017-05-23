package com.escuelaing.arsw.msgbroker.services;

import com.escuelaing.arsw.msgbroker.model.Jugador;
import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import java.util.Set;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2105537
 */
@Service
public class MoveAndPaintRegisterServicesMongoDB implements MoveAndPaintRegisterServices {

    @Override
    public void registerPlayer(Jugador jugadorMovePaint) throws ServicesException {

        MongoClientURI uri = new MongoClientURI("mongodb://ricardopinto08:Insertiniciofin8@ds149431.mlab.com:49431/arsw-projectmoveandpaint");

        MongoClient client = new MongoClient(uri);

        DB db = client.getDB("arsw-projectmoveandpaint");

        DBCollection coll = db.getCollection("Players");

        BasicDBObject whereQuery = new BasicDBObject();

        whereQuery.put("name", jugadorMovePaint.getName());

        DBCursor cursor = coll.find(whereQuery);

        while (cursor.hasNext()) {
            throw new ServicesException("Player found!");
        }

        Gson gson = new Gson();

        BasicDBObject doc = new BasicDBObject("posX", jugadorMovePaint.getPosX()).append("posY", jugadorMovePaint.getPosY()).append("puntajeAcumulado", jugadorMovePaint.getPuntajeAcumulado())
                .append("color", jugadorMovePaint.getColor()).append("name", jugadorMovePaint.getName()).append("email", jugadorMovePaint.getEmail()).append("isPlaying", jugadorMovePaint.getIsPlaying());

        coll.insert(doc);

        client.close();
    }

    @Override
    public Set<Jugador> getPlayersRegistered() throws ServicesException {

        Set<Jugador> players = new ConcurrentSkipListSet<>();

        MongoClientURI uri = new MongoClientURI("mongodb://ricardopinto08:Insertiniciofin8@ds149431.mlab.com:49431/arsw-projectmoveandpaint");

        MongoClient client = new MongoClient(uri);

        DB db = client.getDB("arsw-projectmoveandpaint");

        DBCollection coll = db.getCollection("Players");

        DBCursor cursor = coll.find();
        int i = 1;

        while (cursor.hasNext()) {
            DBObject getPlayer = cursor.next();
            players.add(new Jugador(Integer.parseInt(getPlayer.get("posX").toString()), Integer.parseInt(getPlayer.get("posY").toString()), getPlayer.get("color").toString(), getPlayer.get("name").toString(), getPlayer.get("email").toString(), Boolean.parseBoolean(getPlayer.get("isPlaying").toString())));
            i++;
        }

        client.close();

        return players;
    }

    @Override
    public Jugador getPlayerRegistered(String username) throws ServicesException {
        MongoClientURI uri = new MongoClientURI("mongodb://ricardopinto08:Insertiniciofin8@ds149431.mlab.com:49431/arsw-projectmoveandpaint");

        MongoClient client = new MongoClient(uri);

        DB db = client.getDB("arsw-projectmoveandpaint");

        DBCollection coll = db.getCollection("Players");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", username);

        DBCursor cursor = coll.find(whereQuery);

        while (cursor.hasNext()) {
            DBObject getPlayer = cursor.next();
            return new Jugador(Integer.parseInt(getPlayer.get("posX").toString()), Integer.parseInt(getPlayer.get("posY").toString()), getPlayer.get("color").toString(), getPlayer.get("name").toString(), getPlayer.get("email").toString(), Boolean.parseBoolean(getPlayer.get("isPlaying").toString()));
        }

        throw new ServicesException("Player not found!");

    }

    @Override
    public void addScorePlayer(String name, int score) throws ServicesException {
        /*PENDING*/
    }

    @Override
    public void changeGameState(Jugador j, boolean b) {
        MongoClientURI uri = new MongoClientURI("mongodb://ricardopinto08:Insertiniciofin8@ds149431.mlab.com:49431/arsw-projectmoveandpaint");

        MongoClient client = new MongoClient(uri);

        DB db = client.getDB("arsw-projectmoveandpaint");

        DBCollection coll = db.getCollection("Players");

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append("$set", new BasicDBObject().append("isPlaying", b));

        BasicDBObject searchQuery = new BasicDBObject().append("name", j.getName());

        coll.update(searchQuery, newDocument);

    }

    public MoveAndPaintRegisterServicesMongoDB() {
        Jugador j1 = new Jugador(0, 0, "image", "ricardo", "ricardo@mail.com", false);
        Jugador j2 = new Jugador(10, 0, "asd", "carlos", "carlos@mail.com", false);

        try {
            registerPlayer(j1);
            registerPlayer(j2);
        } catch (ServicesException ex) {
            Logger.getLogger(MoveAndPaintRegisterServicesMongoDB.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

}
