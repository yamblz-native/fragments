package ru.yandex.yamblz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SerG3z on 07.08.16.
 */

public class Artist implements Serializable {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("genres")
    private List<String> genres;
    @SerializedName("tracks")
    private int tracks;
    @SerializedName("albums")
    private int albums;
    @SerializedName("link")
    private String link;
    @SerializedName("description")
    private String description;
    @SerializedName("cover")
    private Cover cover;

    public Artist(long id, String name, String imageBig, String imageSmall, List<String> genres, int tracks, int albums, String link, String description) {
        this.id = id;
        this.name = name;
        this.cover = new Cover(imageSmall, imageBig);
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cover getCover() {
        return cover;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        return id == artist.id &&
                tracks == artist.tracks &&
                albums == artist.albums &&
                link.equals(artist.link) &&
                name.equals(artist.name) &&
                cover.equals(artist.cover) &&
                genres.equals(artist.genres) &&
                description.equals(artist.description);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + cover.hashCode();
        result = 31 * result + genres.hashCode();
        result = 31 * result + tracks;
        result = 31 * result + albums;
        result = 31 * result + link.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover=" + cover +
                ", genres=" + genres +
                ", tracks=" + tracks +
                ", albums=" + albums +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
