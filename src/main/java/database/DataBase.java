package database;

import comment.Comment;
import music.Music;
import suggestion.Suggestion;

import java.sql.*;
import java.util.Objects;

public class DataBase {

    static final String db_url = "jdbc:postgresql://10.227.240.130:5432/pswa0603";
    static final String db_user   = "pswa0603";
    static final String passwd = "UhukZObc";

    public static int numberOfDataBaseQuerys = 0;

    public static void readLogin(){
        numberOfDataBaseQuerys++;
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
        numberOfDataBaseQuerys++;
        // Connect to the DBMS (DataBase Management Server)
        String query  = "INSERT INTO discordando.login (username, password, email, privilege) " + "VALUES ('"+ user_ + "', '" + password_ + "', '" + email_ + "', 0)";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static String[] getAllUsers(){
        numberOfDataBaseQuerys++;

        String query  = "SELECT username FROM discordando.login";
        String[] allusers = new String[100];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                allusers[i++] = res.getString("username");
                //System.out.println(musics[i-1]);
            }
            return allusers;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getAllAdders(){
        numberOfDataBaseQuerys++;

        String query  = "SELECT adder FROM discordando.musics";
        String[] alladders = new String[400];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                alladders[i++] = res.getString("adder");
                //System.out.println(musics[i-1]);
            }
            return alladders;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] searchbyowner(String adder){
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.musics WHERE adder = '" + adder +"';";
        String[] search = new String[400];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                search[i++] = res.getString("name");
                //System.out.println(musics[i-1]);
            }
            return search;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] searchbyownerandgender(String adder, String genre){
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.musics WHERE (adder = '" + adder +"' AND genre = '"+ genre +"');";
        String[] search = new String[400];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                search[i++] = res.getString("name");
                //System.out.println(musics[i-1]);
            }
            return search;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] searchbygender(String genre){
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.musics WHERE genre = '" + genre +"';";
        String[] search = new String[400];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                search[i++] = res.getString("name");
                //System.out.println(musics[i-1]);
            }
            return search;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int removeUser(String user){
        numberOfDataBaseQuerys++;

        String query  = "DELETE FROM discordando.login WHERE username = '" + user + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getUserID(String name){
        numberOfDataBaseQuerys++;

        String query = "SELECT id FROM discordando.login WHERE username = '" + name + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while(res.next())
                return (res.getInt("id"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }



    public static int changepassword(String user_, String password_){
        numberOfDataBaseQuerys++;

        String query = "UPDATE discordando.login " + "SET password ='"+password_+"' " + "WHERE username = '"+user_+"';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }



    public static int changemail(String user_, String mail_){
        numberOfDataBaseQuerys++;

        String query = "UPDATE discordando.login " + "SET email ='"+mail_+"' " + "WHERE username = '"+user_+"';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static int changeuser(String user_, String new_user_){
        numberOfDataBaseQuerys++;

        String query = "UPDATE discordando.login " + "SET username ='"+new_user_+"' " + "WHERE username = '"+user_+"';";

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
        numberOfDataBaseQuerys++;

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
        numberOfDataBaseQuerys++;

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

    public static String getPassword(String user_){
        numberOfDataBaseQuerys++;

        String query  = "SELECT password FROM discordando.login WHERE username = '" + user_ +"'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getString("password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int checkEmail(String user_, String email_){
        numberOfDataBaseQuerys++;

        String query  = "SELECT email FROM discordando.login WHERE username = '" + user_ +"'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                if(Objects.equals(res.getString("email"), email_)){
                    return 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getEmail(String user_){
        numberOfDataBaseQuerys++;

        String query  = "SELECT email FROM discordando.login WHERE username = '" + user_ +"'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {

                return res.getString("email");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int createPlaylist(String name, String creator){
        numberOfDataBaseQuerys++;

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
        numberOfDataBaseQuerys++;

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
        numberOfDataBaseQuerys++;

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

    public static int getPlaylistID(String playlistName, String creator){
        numberOfDataBaseQuerys++;

        String query  = "SELECT id FROM discordando.playlists WHERE name = '" + playlistName +"'" + "AND  creator = '" + creator + "'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static int getNumberSongs(String playlistName, String creator){
        numberOfDataBaseQuerys++;

        String query  = "SELECT nsongs FROM discordando.playlists WHERE name = '" + playlistName +"'" + "AND  creator = '" + creator + "'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getInt("nsongs");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static int getNumberSongs2(int playlistID){
        numberOfDataBaseQuerys++;

        String query  = "SELECT nsongs FROM discordando.playlists WHERE id = " + playlistID + ";";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getInt("nsongs");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static int[] getMusicIDfromPlaylist(int playlistID){
        numberOfDataBaseQuerys++;

        String query  = "SELECT idmusic FROM discordando.attribution WHERE idplaylist = " + playlistID;


        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int[] IDs = new int[55]; int pos = 0;

            while (res.next()) {
                IDs[pos] = res.getInt("idmusic");

                /*
                String queryname = "SELECT name FROM discordando.musics WHERE id = " + IDs[pos] ;
                Statement stmtname = conn.createStatement();
                ResultSet resname = stmtname.executeQuery(queryname);
                resname.next();
                System.out.println("teste: " + resname.getString("name"));
                */

                pos++;
            }

            return IDs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getMusicNameFromID(int musicID){
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.musics WHERE id = " + musicID;

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getMusicURLFromName(String SongName){
        numberOfDataBaseQuerys++;

        String query  = "SELECT url FROM discordando.musics WHERE name = '" + SongName + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getString("url");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

    public static int[] getPlaylistIDandnSongs(String playlistName, String creator){
        numberOfDataBaseQuerys++;
        int[] ID_nSongs = new int[2];

        String query  = "SELECT id, nsongs FROM discordando.playlists WHERE name = '" + playlistName +"'" + "AND  creator = '" + creator + "'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                ID_nSongs[0] = res.getInt("id");
                ID_nSongs[1] = res.getInt("nsongs");
            }
            return ID_nSongs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String[] getAllMusic(){
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.musics";
        String[] musics = new String[100];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                musics[i++] = res.getString("name");
                //System.out.println(musics[i-1]);
            }
            return musics;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int removeMusic(String music){
        numberOfDataBaseQuerys++;

        String query  = "DELETE FROM discordando.musics WHERE name = '" + music + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int addMusic(String name, String genre, String URL, String Adder){
        numberOfDataBaseQuerys++;
        String query  = "INSERT INTO discordando.musics (name, genre, url, adder, time, nlikes, nviews) " + "VALUES ('"+ name + "', '" + genre + "', '" + URL + "','"+Adder+"'," + 0 + "," + 0 +"," + 0 + ");";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int checkMusic(String name){
        numberOfDataBaseQuerys++;
        String query  = "SELECT name FROM discordando.musics";
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                if(Objects.equals(res.getString("name"), name)){
                    return -1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }


        return 1;
    }

    public static int submitSuggestion(String name, String suggestion){
        numberOfDataBaseQuerys++;

        // Connect to the DBMS (DataBase Management Server)
        int zero = 0;
        String query  = "INSERT INTO discordando.suggestions (name, suggestion) " + "VALUES ('"+ name + "', '" + suggestion + "')";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public static String[] getSuggestionsName(){
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.suggestions";
        String[] suggestions = new String[100];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                suggestions[i++] = res.getString("name");

            }
            return suggestions;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Suggestion[] getSuggestionsClass(){
        numberOfDataBaseQuerys++;

        String query  = "SELECT * FROM discordando.suggestions";
        String[] suggestions = new String[100];
        Suggestion[] suggestionsClass = new Suggestion[255];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                suggestionsClass[i] = new Suggestion(res.getString("name"), res.getString("suggestion"));
                //System.out.println(suggestionsClass[i].getName() + suggestionsClass[i].getSuggestion());
                i++;
            }
            return suggestionsClass;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int removeSuggestion(Suggestion suggestion){
        numberOfDataBaseQuerys++;

        String query  = "DELETE FROM discordando.suggestions WHERE (name = '" + suggestion.getName() + "' AND suggestion ='" + suggestion.getSuggestion() + "');";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int checkStatus(String name){
        numberOfDataBaseQuerys++;

        String query  = "SELECT privilege FROM discordando.login WHERE username = '" + name + "';";
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getInt("privilege");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static int askCCStatus(String name){
        numberOfDataBaseQuerys++;

        // Connect to the DBMS (DataBase Management Server)
        int zero = 0;
        String query  = "INSERT INTO discordando.askccstatus (name) " + "VALUES ('" + name + "');";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


    public static String[] getRequests() {
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.askccstatus";
        String[] name = new String[35];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                name[i++] = res.getString("name");
                //System.out.println(musics[i-1]);
            }
            return name;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int givePrivilege(String name) {
        numberOfDataBaseQuerys++;

        String query = "UPDATE discordando.login " + "SET privilege = " + 1 + " " + "WHERE username = '"+ name + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        numberOfDataBaseQuerys++;

        String query2  = "DELETE FROM discordando.askccstatus WHERE name = '" + name + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query2);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int removeRequest(String name){
        numberOfDataBaseQuerys++;

        String query2  = "DELETE FROM discordando.askccstatus WHERE name = '" + name + "';";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query2);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int checkCCRequest(String name){
        numberOfDataBaseQuerys++;

        String query  = "SELECT name FROM discordando.askccstatus";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                if(Objects.equals(name, res.getString("name"))) return 1;
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static int submitComment(String name, String comment, String music){
        numberOfDataBaseQuerys++;

        // Connect to the DBMS (DataBase Management Server)
        String query  = "INSERT INTO discordando.comments (name, comment, music) " + "VALUES ('"+ name + "', '" + comment + "', '" + music + "')";
        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public static Comment[] getCommentsClass(){
        numberOfDataBaseQuerys++;

        String query  = "SELECT * FROM discordando.comments";
        String[] comments = new String[100];
        Comment[] commentClass = new Comment[255];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                commentClass[i] = new Comment(res.getString("name"), res.getString("comment"), res.getString("music"));
                i++;
            }
            return commentClass;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int removeComment(Comment comment){
        numberOfDataBaseQuerys++;

        String query  = "DELETE FROM discordando.comments WHERE (name = '" + comment.getName() + "' AND comment ='" + comment.getComment() + /*"' AND musica ='" + comment.getMusica() +*/ "');";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int alreadylike(int idmusic, int iduser){
        numberOfDataBaseQuerys++;

        String query  = "Select iduser FROM discordando.likes WHERE idmusic = " + idmusic + ";";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while(res.next()){
                int x = res.getInt("iduser");

                //System.out.println("x = " + x + "user = " + iduser);

                if(x == iduser) return 1;
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getMusicIDfromName(String name){
        numberOfDataBaseQuerys++;

        String query  = "SELECT id FROM discordando.musics WHERE name = '" + name + "'";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                return res.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static int addLike(int idmusic, int iduser){
        numberOfDataBaseQuerys++;
        String query  = "INSERT INTO discordando.likes (idmusic, iduser) " + "VALUES (" + idmusic + ", " + iduser + "); ";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int removeLike(int idmusic, int iduser){
        numberOfDataBaseQuerys++;

        //String query  = "DELETE FROM discordando.comments WHERE (name = '" + comment.getName() + "' AND comment ='" + comment.getComment() + "');";
        String query  = "DELETE FROM discordando.likes WHERE (iduser =  " + iduser + "AND idmusic = " + idmusic + ");";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int[] getAllLikes(int iduser){
        numberOfDataBaseQuerys++;

        String query  = "SELECT idmusic FROM discordando.likes WHERE iduser =  " + iduser +  ";";

        int[] ids = new int[30];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while(res.next()){
                ids[i++] = res.getInt("idmusic");
            }

            return ids;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Music[] getMusicsFromUser(String creator) {
        numberOfDataBaseQuerys++;

        String query  = "SELECT * FROM discordando.musics WHERE adder = '" + creator + "';";
        Music[] musicClass = new Music[255];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                musicClass[i] = new Music(res.getInt("id"), res.getString("name"), res.getString("url"));
                i++;
            }
            return musicClass;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int addMusicToPlaylist(int idmusic, int idplaylist){
        numberOfDataBaseQuerys++;
        String query  = "INSERT INTO discordando.attribution (idplaylist, idmusic) " + "VALUES (" + idplaylist + ", " + idmusic + "); ";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int updatenSgons(int id, int nsongs){
        numberOfDataBaseQuerys++;

        String query = "UPDATE discordando.playlists " + "SET nsongs = "+nsongs+" " + "WHERE id =  " + id + ";";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public static int removeMusicLikes(int idmusic){
        numberOfDataBaseQuerys++;

        //String query  = "DELETE FROM discordando.comments WHERE (name = '" + comment.getName() + "' AND comment ='" + comment.getComment() + "');";
        String query  = "DELETE FROM discordando.attribution WHERE (idmusic =  " + idmusic + ");";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int removeMusicFromPlaylist(int idmusic, int idplaylist){
        numberOfDataBaseQuerys++;

        //String query  = "DELETE FROM discordando.comments WHERE (name = '" + comment.getName() + "' AND comment ='" + comment.getComment() + "');";
        String query  = "DELETE FROM discordando.attribution WHERE (idmusic =  " + idmusic + "AND idplaylist = " + idplaylist + ");";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int[] playlistsFromMusic(int idmusic){
        numberOfDataBaseQuerys++;

        String query  = "SELECT idplaylist FROM discordando.attribution WHERE idmusic = " + idmusic + ";";
        int[] IDs = new int[255];

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {

            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            int i = 0;
            while (res.next()) {
                IDs[i] = res.getInt("idplaylist");
                i++;
            }
            return IDs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int removeAttribution(int idmusic, int idplaylist){
        numberOfDataBaseQuerys++;

        String query  = "DELETE FROM discordando.attribution WHERE (idmusic =  " + idmusic + "AND idplaylist = " + idplaylist + ");";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int deletePlaylist(int id){
        numberOfDataBaseQuerys++;

        String query  = "DELETE FROM discordando.playlists WHERE id = " + id + ";";

        try(Connection conn = DriverManager.getConnection(db_url, db_user, passwd);) {


            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);

            if(res == 0 ) return -1;
            else return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
