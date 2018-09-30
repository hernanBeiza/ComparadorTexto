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
}
