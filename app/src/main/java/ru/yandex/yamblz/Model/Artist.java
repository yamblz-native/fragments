package ru.yandex.yamblz.Model;

/**
 * Created by dan on 10.08.16.
 */

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danil on 25.04.16.
 */
public class Artist implements Serializable {
    private long id ;
    private String name = "";
    private long tracks;
    private long albums;
    private String link = "";
    private String description = "";
    private List<String> genres;
    private Cover cover;
    private String genresString = "";


    public Artist() {
        genres = new ArrayList<>();
        this.cover = new Cover();
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public String getGenres() {
        if (genresString.length() > 0)  {
            return genresString;
        }

        StringBuilder concatination = new StringBuilder();
        for(String s: genres) {
            if (concatination.length() != 0) {
                concatination.append(", ");
            }
            concatination.append(s);

        }
        if (genresString.length() == 0) {
            genresString = concatination.toString();
        }

        return concatination.toString();
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tracks=" + tracks +
                ", albums=" + albums +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", genres=" + genres +
                ", cover=" + cover +
                '}';
    }

    public String getInfo() {
        return String.valueOf(albums) + " альбомов, " + String.valueOf(tracks) + " песен"  ;
    }

    public void setGenresString(String genres) {
        genresString = genres;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTracks(long tracks) {
        this.tracks = tracks;
    }

    public void setAlbums(long albums) {
        this.albums = albums;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return this.id;
    }

    public long getTracks() {
        return this.tracks;
    }

    public long getAlbums() {
        return this.albums;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
