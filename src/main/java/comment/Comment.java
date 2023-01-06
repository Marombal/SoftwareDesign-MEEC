package comment;

import Controllers.musicMenuController;
import music.Music;

public class Comment {
    private String Name;
    private String comment;

    private String musica;



    public Comment(){};

    public Comment(String name, String comm, String song){
        this.Name = name;
        this.comment = comm;
        this.musica = song;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public String getComment() {
        return comment;
    }
    public String getMusica(){
        return musica;
    }
}
