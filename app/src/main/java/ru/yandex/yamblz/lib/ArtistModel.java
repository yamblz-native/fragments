package ru.yandex.yamblz.lib;

import android.content.ContentValues;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Cover cover = (Cover) o;

            if (small != null ? !small.equals(cover.small) : cover.small != null) return false;
            return big != null ? big.equals(cover.big) : cover.big == null;

        }

        @Override
        public int hashCode() {
            int result = small != null ? small.hashCode() : 0;
            result = 31 * result + (big != null ? big.hashCode() : 0);
            return result;
        }
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistModel that = (ArtistModel) o;

        if (id != that.id) return false;
        if (tracks != that.tracks) return false;
        if (albums != that.albums) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (cover!=that.cover){//if null
            if(cover==null || that.cover==null)return false;
            if(!cover.equals(that.cover))return false;
        }
        if (genres!=that.genres){//if null
            if(genres==null || that.genres==null)return false;
            if(!genres.containsAll(that.genres) && !that.genres.containsAll(genres))return false;
        }
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + tracks;
        result = 31 * result + albums;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
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

    public static ContentValues artistModelToValues(ArtistModel model){
        ContentValues values = new ContentValues();
        values.put(ContentProviderContract.Artists.ID, model.getId());
        values.put(ContentProviderContract.Artists.NAME, model.getName());
        values.put(ContentProviderContract.Artists.TRACKS, model.getTracks());
        values.put(ContentProviderContract.Artists.ALBUMS, model.getAlbums());
        values.put(ContentProviderContract.Artists.LINK, model.getLink());
        values.put(ContentProviderContract.Artists.DESCRIPTION, model.getDescription());
        values.put(ContentProviderContract.Artists.IMAGE_BIG, model.getBigImage());
        values.put(ContentProviderContract.Artists.IMAGE_SMALL, model.getSmallImage());
        String genres="";
        for (String genre : model.getGenres()) {
           // int genreId = getGenreId(db, genre);
         //   values.clear();
         //   values.put(ContentProviderContract.ArtistGenres.ARTIST_ID, id);
          //  values.put(ContentProviderContract.ArtistGenres.GENRE_ID, genreId);
          //  db.insert(ARTIST_GENRES, null, values);
            genres+=genre+" ";
        }
        values.put(ContentProviderContract.Artists.GENRES,genres);
        //db.insert(ARTISTS, null, values);
        return values;

    }


}
