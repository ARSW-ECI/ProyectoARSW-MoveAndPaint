/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.escuelaing.arsw.msgbroker.security;

import com.escuelaing.arsw.msgbroker.controllers.MoveAndPaintRESTController;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import org.apache.log4j.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author ricar
 */
public class PasswordUtil {
    
    private static final Random random = new Random();
    
    final static Logger logger = Logger.getLogger(MoveAndPaintRESTController.class);
	
    /**
     * Obtiene el hash y la sal a partir de una contraseña 
     */
    public static HashSalt getHash(String password) throws Exception {		
        byte[] salt = new byte[16];
	// Genera la sal de forma aleatoria
	random.nextBytes(salt);
	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
	try {
	    // Obtiene una instancia para el algoritmo
	    SecretKeyFactory f = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
	    // Genera el hash
            byte[] hash = f.generateSecret(spec).getEncoded();
	    Base64.Encoder enc = Base64.getEncoder();
	    // convierte hash y sal a cadena de texto
	    return new HashSalt(enc.encodeToString(hash), enc.encodeToString(salt));
	}
	catch(NoSuchAlgorithmException e) {
	    logger.debug(e);
	}
	catch (InvalidKeySpecException e) {
	    logger.debug(e);
	}
		
	Exception e= new Exception("No se pudo crear hash");
        logger.debug(e);
        return null;
    }
	
    public static boolean ValidatePass(String password, String stringHash, String stringSalt) {
        Base64.Decoder dec = Base64.getDecoder();
	// Convierte la sal a arreglo de bytes
	byte[] salt = dec.decode(stringSalt);
	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
	try {
	    // Obtiene instancia del algoritmo
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
	    // Genera el hash como arreglo de bytes
	    byte[] hash = f.generateSecret(spec).getEncoded();
	    Base64.Encoder enc = Base64.getEncoder();
            // convierte el hash a cadena de texto
	    String currentHash = enc.encodeToString(hash);
            // compara si los hash son iguales
	    return currentHash.equals(stringHash);
	}
	catch(NoSuchAlgorithmException e) {
	    logger.debug(e);
	}
	catch (InvalidKeySpecException e) {
	    logger.debug(e);
	}
	
	return false;
    }
}