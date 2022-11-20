package com.example.discordandosb;

import java.sql.*;
import java.util.Objects;

public class DataBase {

    static final String db_url = "jdbc:postgresql://10.227.240.130:5432/pswa0603";
    static final String db_user   = "pswa0603";
    static final String passwd = "UhukZObc";

    public static void readLogin(){
        String query  = "SELECT id, username, password, email FROM discordando.login";
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                System.out.print("ID: "          + rs.getInt("id"));
                System.out.print(", user: " + rs.getString("username"));
                System.out.print(", password: "      + rs.getString("password"));
                System.out.print(", email: "      + rs.getString("email"));
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addLogin(String user_, String password_, String email_){
        // Connect to the DBMS (DataBase Management Server)
        String query  = "INSERT INTO discordando.login (username, password, email) " + "VALUES ('"+ user_ + "', '" + password_ + "', '" + email_ + "')";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    /*
    * This function will tell you if a given Username is already in the login DataBase or not
    *
    * Param: String user_ -> The username that you want to search for
    *
    * Return: 1 if the username already exist, 0 if it doesn't
    * */
    public static int findUsername(String user_){
        String query  = "SELECT username FROM discordando.login";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                if(Objects.equals(res.getString("username"), user_)){
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    /*
     * This function will tell you if a given Username and Password match each other
     *
     * Param:   String user_ -> The username
     *          String password_ -> The password
     *
     * Return: 1 if the yes, 0 if it doesn't
     * */
    public static int checkPassword(String user_, String password_){
        String query  = "SELECT password FROM discordando.login WHERE username = '" + user_ +"'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                if(Objects.equals(res.getString("password"), password_)){
                    return 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int createPlaylist(String name, String creator){
        // Connect to the DBMS (DataBase Management Server)
        int zero = 0;
        String query  = "INSERT INTO discordando.playlists (name, creator, nsongs, nlikes) " + "VALUES ('"+ name + "', '" + creator + "', " + zero + "," + zero + ")";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String[] findPlaylists(String creator){
        String query  = "SELECT name FROM discordando.playlists WHERE creator = '" + creator +"'";

        String[] playlist = new String[255];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                //System.out.println(res.getString("name"));
                playlist[i] = res.getString("name");
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return playlist;
    }

    public static String[] findMusicsFromPlaylist(String playlistName){
        String query  = "SELECT name FROM discordando.musics WHERE playlist = '" + playlistName +"'";

        String[] musics = new String[255];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                //System.out.println(res.getString("name"));
                musics[i] = res.getString("name");
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return musics;
    }

}
