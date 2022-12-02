package db;

import net.sourceforge.jtds.jdbc.DateTime;

public class Tiempo {
    
    private DateTime fecha;
    private String zona;   
    private int temperatura;
    private float velViento;
    private float dirViento;
    private double porcPrecipitación;    
    private String estado;
    private int idPlaca;
    private String email;

    public Tiempo(DateTime fecha, String zona, int temperatura, float velViento, float dirViento, double porcPrecipitación, String estado, int idPlaca, String email) {
        this.fecha = fecha;
        this.zona = zona;
        this.temperatura = temperatura;
        this.velViento = velViento;
        this.dirViento = dirViento;
        this.porcPrecipitación = porcPrecipitación;
        this.estado = estado;
        this.idPlaca = idPlaca;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public double getPorcPrecipitación() {
        return porcPrecipitación;
    }

    public void setPorcPrecipitación(double porcPrecipitación) {
        this.porcPrecipitación = porcPrecipitación;
    }


    public float getDirViento() {
        return dirViento;
    }

    public void setDirViento(float dirViento) {
        this.dirViento = dirViento;
    }


    public float getVelViento() {
        return velViento;
    }

    public void setVelViento(float velViento) {
        this.velViento = velViento;
    }


    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }


    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }


    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }
    
    public int getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(int idPlaca) {
        this.idPlaca = idPlaca;
    }

}
