package db;

public class Placa {
    
    private int idPlaca;
    private String ubicacion;
    private int orientacion;
    private int idUser;

    public Placa(int idPlaca, String ubicacion, int orientacion, int idUser) {
        this.idPlaca = idPlaca;
        this.ubicacion = ubicacion;
        this.orientacion = orientacion;
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
