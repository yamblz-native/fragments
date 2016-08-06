package ru.yandex.yamblz.lib;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Модель артиста,генерируется GSON
public class ArtistModel {
    private long id;
    private String name;
    private Cover cover;
    private List<String> genres;
    private int tracks;
    private int albums;
    private String link;
    private String description;

    public ArtistModel() {
    }

    public ArtistModel(long id, String name, String imageBig, String imageSmall, List<String> genres, int tracks, int albums, String link, String description) {
        this.id = id;
        this.name = name;
        this.cover = new Cover();
        cover.big=imageBig;
        cover.small=imageSmall;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ArtistModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", smallImageUrl='" + cover.small + '\'' +
                ", bigImageUrl='" + cover.big + '\'' +
                ", genres=" + genres +
                ", tracks=" + tracks +
                ", albums=" + albums +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
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

    public String getBigImage() {
        return cover.big;
    }

    public String getSmallImage() {
        return cover.small;
    }

    private static class Cover {
        public String small;
        public String big;
    }

    public static ArtistModel cursorToModel(Cursor cursor) {
        long id = cursor.getLong(0);
        String name = cursor.getString(1);
        int tracks = cursor.getInt(2);
        int albums = cursor.getInt(3);
        String link = cursor.getString(4);
        String description = cursor.getString(5);
        String imageBig = cursor.getString(6);
        String imageSmall = cursor.getString(7);
        String genres = cursor.getString(11);
        List<String> genresList;
        if (genres == null) {
            genresList = new ArrayList<>();
        } else {
            genresList = Arrays.asList(genres.split(", "));
        }
        return new ArtistModel(id, name, imageBig, imageSmall, genresList, tracks, albums, link, description);
    }
}
