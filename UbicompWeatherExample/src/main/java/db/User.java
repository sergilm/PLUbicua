package db;

public class User {
    
    private String email;
    private String password;
    private String name;
    private int idUser;

    public User(String email, String password, String name, int idUser) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.idUser = idUser;
    }
    
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
