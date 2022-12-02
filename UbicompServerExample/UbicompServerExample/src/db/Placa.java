package db;

public class Placa {
    
    private int idPlaca;
    private String ubicacion;
    private int orientacion;
    private String email;

    public Placa(int idPlaca, String ubicacion, int orientacion, String email) {
        this.idPlaca = idPlaca;
        this.ubicacion = ubicacion;
        this.orientacion = orientacion;
        this.email = email;
    }
    
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }


    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public int getIdPlaca() {
        return idPlaca;
    }

    public void setIdPlaca(int idPlaca) {
        this.idPlaca = idPlaca;
    }

}
