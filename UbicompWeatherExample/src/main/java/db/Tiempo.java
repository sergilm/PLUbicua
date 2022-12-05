/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.util.Date;

/**
 *
 * @author 34629
 */
public class Tiempo {

    private int idPlaca;
    private String estado;
    private double porcPrecipitacion;
    private int dirViento;
    private double velViento;
    private double temperatura;
    private Date fecha;
    private int idZona;
    
    public Tiempo(Date fecha, double temperatura, double velViento, int dirViento, double porcPrecipitación, String estado, int idPlaca, int idZona) {
        this.idPlaca = idPlaca;
        this.estado = estado;
        this.porcPrecipitacion = porcPrecipitacion;
        this.dirViento = dirViento;
        this.velViento = velViento;
        this.temperatura = temperatura;
        this.fecha = fecha;
        this.idZona = idZona;
    }
    
    public Tiempo(){
        
    }

    public int getIdZona() {
        return idZona;
    }

    /**
     * Set the value of idPlaca
     *
     * @param idPlaca new value of idPlaca
     */
    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }
    /**
     * Get the value of idPlaca
     *
     * @return the value of idPlaca
     */
    public int getIdPlaca() {
        return idPlaca;
    }

    /**
     * Set the value of idPlaca
     *
     * @param idPlaca new value of idPlaca
     */
    public void setIdPlaca(int idPlaca) {
        this.idPlaca = idPlaca;
    }


    /**
     * Get the value of estado
     *
     * @return the value of estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Set the value of estado
     *
     * @param estado new value of estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    private double porcPreciipitacion;

    /**
     * Get the value of porcPreciipitacion
     *
     * @return the value of porcPreciipitacion
     */
    public double getPorcPrecipitacion() {
        return porcPrecipitacion;
    }

    /**
     * Set the value of porcPreciipitacion
     *
     * @param porcPreciipitacion new value of porcPreciipitacion
     */
    public void setPorcPrecipitacion(double porcPrecipitacion) {
        this.porcPrecipitacion = porcPrecipitacion;
    }


    /**
     * Get the value of dirViento
     *
     * @return the value of dirViento
     */
    public int getDirViento() {
        return dirViento;
    }

    /**
     * Set the value of dirViento
     *
     * @param dirViento new value of dirViento
     */
    public void setDirViento(int dirViento) {
        this.dirViento = dirViento;
    }


    /**
     * Get the value of velViento
     *
     * @return the value of velViento
     */
    public double getVelViento() {
        return velViento;
    }

    /**
     * Set the value of velViento
     *
     * @param velViento new value of velViento
     */
    public void setVelViento(double velViento) {
        this.velViento = velViento;
    }


    /**
     * Get the value of temperatura
     *
     * @return the value of temperatura
     */
    public double getTemperatura() {
        return temperatura;
    }

    /**
     * Set the value of temperatura
     *
     * @param temperatura new value of temperatura
     */
    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }


    /**
     * Get the value of zona
     *
     * @return the value of zona
     */

    /**
     * Get the value of fecha
     *
     * @return the value of fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Set the value of fecha
     *
     * @param fecha new value of fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
