package ru.yandex.yamblz.artists;

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
}
