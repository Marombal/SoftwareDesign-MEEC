package user;

import java.util.Objects;

public class User {
    private String email;
    public static String name;
    private String password;
    private static int ID;
    private int numberOfPlaylists;
    private static int  privilege;
    private static String[] historic = {"", "", "", "", "", "", "", "", "" , "" , "" , "" ,"", "", "", "", "","","",""};
    private static final int size = 20;
    public static String getName(){
        return name;
    }

    public static int getID(){ return ID;}
    public static void setID(int id){ ID = id;}

    public static void setName(String newName){
        name = newName;
    }

    public static int getPrivilege(){
        return privilege;
    }

    public static void setPrivilege(int p){
        privilege = p;
    }

    public static String[] getHistoric(){
        return historic;
    }

    public static void updateHistoric(String lastMusic){

        boolean update = true;
        for(int i = 0; i < size; i++){
            if (Objects.equals(lastMusic, historic[i])) {
                update = false;
                break;
            }
        }
        if(update){
            for(int i = size-1; i > 0; i--){
                historic[i] = historic[i - 1];
            }
            historic[0] = lastMusic;
        }

    }

    public void setNumberOfPlaylists(int numberOfPlaylists) {
        this.numberOfPlaylists = numberOfPlaylists;
    }
}
