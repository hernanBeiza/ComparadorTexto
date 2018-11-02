/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hiperactivo.comparador.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author hb
 */
public class ArchivoController {
        //public String abrirArchivo(String ruta, Charset encoding) throws IOException {
    public static final String abrirArchivo(File archivo) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(archivo.getAbsolutePath()));
            return new String(encoded, Charset.defaultCharset());
        } catch(IOException ex){
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }
    
    
    public static final String obtenerHashDeArchivo(File archivo) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            try {
                md.update(FileUtils.readFileToByteArray(archivo));
            } catch (IOException ex) {
                Logger.getLogger(ArchivoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] digest = md.digest();
            String myChecksum = DatatypeConverter.printHexBinary(digest).toUpperCase();
            System.out.println(myChecksum);
            return myChecksum;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComparadorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
