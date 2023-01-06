package music;

import database.DataBase;

public class Music {
    private int id;
    private String name, url;
    private int time;
    private int nLikes;

    public Music(){}

    public Music(int newId){
        this.id = newId;
        // data base
        this.name = DataBase.getMusicNameFromID(this.id);
    }

    public Music(int ID, String NAME, String URL){
        this.id = ID;
        this.name = NAME;
        this.url = URL;
    }

    public Music(String name){
        //this.id = newId;
        // data base
        this.name = name;
        this.url = DataBase.getMusicURLFromName(this.name);
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setTime(int time) {
        this.time = time;
    }

    public void setnLikes(int nLikes) {
        this.nLikes = nLikes;
    }

    public int getnLikes() {
        return nLikes;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
    public String getLink(){
        return url;
    }
}
