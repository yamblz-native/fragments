package ru.yandex.yamblz.euv.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data model.
 * Used both in Provider (as output of JSON parser) and Client.
 */
public class Artist implements Serializable {
    private int id;
    private String name;
    private List<String> genres = new ArrayList<>();
    private int tracks;
    private int albums;
    private String link;
    private String description;
    private Cover cover;

    public Artist() { /* default constructor for the JSON parser */ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getGenresStr() {
        StringBuilder sb = new StringBuilder();
        int size = genres.size();
        for (int i = 0; i < size; i++) {
            sb.append(genres.get(i));
            if (i + 1 < size) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", tracks=" + tracks +
                ", albums=" + albums +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", cover=" + cover +
                '}';
    }
}
