/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.hiperactivo.comparador.models;

/**
 *
 * @author hb
 */
public class Linea {
    
    private Integer inicio;
    private Integer fin;

    public Linea(Integer inicio, Integer fin) {
        this.inicio = inicio;
        this.fin = fin;
    }
    
    
    public Integer getInicio() {
        return inicio;
    }

    public void setInicio(Integer inicio) {
        this.inicio = inicio;
    }

    public Integer getFin() {
        return fin;
    }

    public void setFin(Integer fin) {
        this.fin = fin;
    }
    
    
    
}
