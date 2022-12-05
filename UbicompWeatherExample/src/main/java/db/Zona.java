/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author 34629
 */
public class Zona {
    
    private int idZona;
    private String nombre;

    public Zona(int idZona, String nombre) {
        this.idZona = idZona;
        this.nombre = nombre;
    }

    
    /**
     * Get the value of nombre
     *
     * @return the value of nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Set the value of nombre
     *
     * @param nombre new value of nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Get the value of idZona
     *
     * @return the value of idZona
     */
    public int getIdZona() {
        return idZona;
    }

    /**
     * Set the value of idZona
     *
     * @param idZona new value of idZona
     */
    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

}
