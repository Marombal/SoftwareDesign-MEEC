package playlist;

import database.DataBase;
import javafx.scene.chart.PieChart;
import music.Music;

import java.util.Objects;

public class Playlist {
    private int id;
    private int nSongs;
    private int nLikes;
    private String name;
    private String genre;
    private String creator;

    private static final int MAXmusics = 25;
    private Music[] musics = new Music[MAXmusics];

    public Playlist(){}
    public Playlist(String newName, String newCreator){
        this.name = newName;
        this.creator = newCreator;

        /* not efficient / Spending 2 request */
        //this.id = DataBase.getPlaylistID(this.name , this.creator);
        //this.nSongs = DataBase.getNumberSongs(this.name, this.creator);
        //DataBase.getPlaylistIDandnSongs(this.name, this.creator);

        /* MORE EFFICIENT !!! Get both ID and nSongs in the same query*/
        int[] idAndnSongs = DataBase.getPlaylistIDandnSongs(this.name, this.creator);
        assert idAndnSongs != null;
        this.id = idAndnSongs[0];
        this.nSongs = idAndnSongs[1];

        int[] mus = DataBase.getMusicIDfromPlaylist(this.id);
        for(int i = 0; i < nSongs; i++){
            assert mus != null;
            musics[i] = new Music(mus[i]);
        }
    }

    public Playlist(String newCreator){
        if(Objects.equals(newCreator, "all")){
            this.name = "All Musics";
            this.creator = "all";
            String[] music = DataBase.getAllMusic();
            int i = 0;
            while(true){
                //System.out.println("-->" + i + music[i]);
                assert music != null;
                if (music[i] == null) break;
                musics[i] = new Music(music[i]);
                i++;
            }
            this.nSongs = i;
            //System.out.println("final-> "+this.nSongs);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Music[] getMusics() {
        return musics;
    }

    public int getnSongs() {
        return nSongs;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void updatePlaylist(){
        int[] idAndnSongs = DataBase.getPlaylistIDandnSongs(this.name, this.creator);
        assert idAndnSongs != null;
        this.id = idAndnSongs[0];
        this.nSongs = idAndnSongs[1];

        int[] mus = DataBase.getMusicIDfromPlaylist(this.id);
        for(int i = 0; i < nSongs; i++){
            assert mus != null;
            musics[i] = new Music(mus[i]);
        }
    }
}
