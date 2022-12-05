package db;

public class SensorLuz {
    
    private int idSensorLuz;
    private int luminosidad;
    private int idPlaca;

    public SensorLuz(int idSensorLuz, int luminosidad, int idPlaca) {
        this.idSensorLuz = idSensorLuz;
        this.luminosidad = luminosidad;
        this.idPlaca = idPlaca;
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


    public int getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(int luminosidad) {
        this.luminosidad = luminosidad;
    }


    public int getIdSensorLuz() {
        return idSensorLuz;
    }

    public void setIdSensorLuz(int idSensorLuz) {
        this.idSensorLuz = idSensorLuz;
    }

}
