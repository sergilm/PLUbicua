package db;

public class SensorLuz {
    
    private int idSensorLuz;
    private int luminosidad;
    private int idPlaca;
    private String email;

    public SensorLuz(int idSensorLuz, int luminosidad, int idPlaca, String email) {
        this.idSensorLuz = idSensorLuz;
        this.luminosidad = luminosidad;
        this.idPlaca = idPlaca;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
