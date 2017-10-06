package edu.uco.mcamposcardoso.kittracker.types;

/**
 * Created by matheuscamposcardoso on 05/06/17.
 */
public class CurrentUser {

    private String token = Token.TOKEN;
    private static CurrentUser currentUser;

    private CurrentUser() {}

    public static synchronized CurrentUser getInstance(){
        if(currentUser == null){
            currentUser = new CurrentUser();
        }
        return currentUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
