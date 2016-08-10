package ru.yandex.yamblz.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SerG3z on 07.08.16.
 */

public class Artist implements Parcelable {
    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
    private int id;
    private String name;
    private List<String> genres;
    private Cover cover;
    private int tracks;
    private int albums;
    private String description;
    private String link;

    private Artist(int id, String name, List<String> genres, Cover cover, int tracks, int albums, String description, String link) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.cover = cover;
        this.tracks = tracks;
        this.albums = albums;
        this.description = description;
        this.link = link;
    }

    private Artist(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.genres = in.createStringArrayList();
        this.cover = in.readParcelable(Cover.class.getClassLoader());
        this.tracks = in.readInt();
        this.albums = in.readInt();
        this.description = in.readString();
        this.link = in.readString();
    }

    public static Artist getArtistFromCursor(Cursor cursor) {
        String genresString = cursor.getString(8);
        List<String> genres = new ArrayList<>(Collections.singletonList(genresString));

        return new Artist.Builder()
                .setName(cursor.getString(1))
                .setLink(cursor.getString(2))
                .setTracks(cursor.getInt(3))
                .setAlbums(cursor.getInt(4))
                .setCover(new Cover(cursor.getString(6), cursor.getString(5)))
                .setDescription(cursor.getString(7))
                .setGenres(genres)
                .build();
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public int getTracks() {
        return tracks;
    }

    public int getAlbums() {
        return albums;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Cover getCover() {
        return cover;
    }

    public String getGenres() {
        return TextUtils.join(", ", genres);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.genres);
        dest.writeParcelable(this.cover, flags);
        dest.writeInt(this.tracks);
        dest.writeInt(this.albums);
        dest.writeString(this.description);
        dest.writeString(this.link);
    }

    @Override
    public String toString() {
        return "Artist: " + name;
    }

    public static class Builder {
        private int id;
        private String name;
        private List<String> genres;
        private Cover cover;
        private int tracks;
        private int albums;
        private String description;
        private String link;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setGenres(List<String> genres) {
            this.genres = genres;
            return this;
        }

        public Builder setCover(Cover cover) {
            this.cover = cover;
            return this;
        }

        public Builder setTracks(int tracks) {
            this.tracks = tracks;
            return this;
        }

        public Builder setAlbums(int albums) {
            this.albums = albums;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public Artist build() {
            return new Artist(id, name, genres, cover, tracks, albums, description, link);
        }
    }
}
