/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hiperactivo.comparador.controllers;

import cl.hiperactivo.comparador.models.Linea;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author hb
 */
public class ComparadorController {
    
    public ComparadorDelegate delegate;

    
    public interface ComparadorDelegate {        
        void onComparadorPorLineaEncontradas(ArrayList<Linea> lineas);
        void onComparadorPorLineaNoEncontradas();
        
        void onComparadorDeContenidoExito(String mensaje);
        void onComparadorDeContenidoError(String error);        
    }

    public ComparadorDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(ComparadorDelegate delegate) {
        this.delegate = delegate;
    }
    /**
     * Compara si dos archivos son iguales o no a través de la librería FileUtils
     * @param primero File Primer archivo
     * @param segundo File Segundo archivo
     */
    public void compararIgualdadDeContenido(File primero, File segundo){
        boolean comparar = true;
        String errores = "Faltó:";
        
        if(primero==null){
            comparar = false;
            errores+="Seleccionar el primer archivo";
        }
        if(segundo==null){
            comparar = false;
            errores+="Seleccionar el segundo archivo";
        }
            
        if(comparar){
            try {
                if(FileUtils.contentEquals(primero, segundo)){
                    this.delegate.onComparadorDeContenidoExito("Son iguales los archivos");                   
                } else {
                    this.delegate.onComparadorDeContenidoError("No son iguales");
                }
            } catch (IOException ex) {
                System.out.println(ex.getLocalizedMessage());
                this.delegate.onComparadorDeContenidoError("Error al tratar de comprar los archivos");
            }
        }
    }
    /**
     * Compara dos archivos línea a línea
     * @param primero File Primer archivo
     * @param segundo File Segundo archivo
     */
    public void compararIgualdadLineaALinea(File primero, File segundo){
        boolean comparar = true;
        String errores = "Faltó:";
        
        if(primero==null){
            comparar = false;
            errores+="Seleccionar el primer archivo";
        }
        if(segundo==null){
            comparar = false;
            errores+="Seleccionar el segundo archivo";
        }
        
        if(comparar){
            try {
                ArrayList<String> lines = (ArrayList)FileUtils.readLines(primero, Charset.defaultCharset());
                //System.out.println(lines);

                ArrayList<String> lines2 = (ArrayList)FileUtils.readLines(segundo, Charset.defaultCharset());
                //System.out.println(lines2);
                
                //TODO obtener el archivo más largo, y hacer la comparación según el archivo que sea más largo
                /*
                for (int i = 0; i < lines.size(); i++) {
                    String linea = lines.get(i);
                    String linea2 = lines2.get(i);
                    if(linea.equals(linea2)){
                        System.out.println("Linea "+ i + " de archivo " + primero.getName() + " ES IGUAL a la linea " + i + " de archivo " + segundo.getName());
                    } else {
                        System.out.println("Linea "+ i + " de archivo " + primero.getName() + " NO ES IGUAL a la linea " + i + " de archivo " + segundo.getName());
                    }
		}
                */
                //https://stackoverflow.com/questions/31426187/want-to-find-content-difference-between-two-text-files-with-java

                //Diferencias extras
                ArrayList<String> tmpLines1 = new ArrayList<String>(lines);
                tmpLines1.removeAll(lines2);
                System.out.println("Contenido de " + primero.getName() + " que no está en " + segundo.getName());
                System.out.println(tmpLines1);
                
                ArrayList<String> tmpLines2 = new ArrayList<String>(lines2);
                tmpLines2.removeAll(lines);
                System.out.println("Contenido de " + segundo.getName() + " que no está en " + primero.getName());
                System.out.println(tmpLines2);
                                               
                String unoCompleto = FileUtils.readFileToString(primero, Charset.defaultCharset());
                String dosCompleto = FileUtils.readFileToString(segundo, Charset.defaultCharset());
                
                ArrayList<Linea> encontradas = new ArrayList<Linea>();
                for(String linea:tmpLines2){
                    System.out.println(linea);
                    int inicio = dosCompleto.indexOf(linea);
                    int fin = inicio+linea.length();//dosCompleto.lastIndexOf(linea);
                    System.out.println("inicio " + inicio);
                    System.out.println("fin " + fin);                        
                    Linea model = new Linea(new Integer(inicio),new Integer(fin));
                    encontradas.add(model);
                }
                if(encontradas.size()>0){
                    delegate.onComparadorPorLineaEncontradas(encontradas);                
                } else {
                    delegate.onComparadorPorLineaNoEncontradas();
                }
                
            } catch (IOException ex) {
                System.out.println(ex.getLocalizedMessage());
                //delegate.onComparadorPorLineaError("Error al obtener las líneas por archivo");
            }
        } else {
            System.out.println(errores);
        }
    }

}
