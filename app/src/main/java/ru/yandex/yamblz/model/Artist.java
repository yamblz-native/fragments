package ru.yandex.yamblz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Artist implements Parcelable {
    public final int id;
    public final String name;
    public final List<String> genres;
    public final int tracks;
    public final int albums;
    public final String link;
    public final String description;
    public final Cover cover;

    public Artist(int id, String name, List<String> genres, int tracks, int albums, String link, String description, Cover cover) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;
        this.cover = cover;
    }

    public static class Cover implements Parcelable {
        public final String small;
        public final String big;

        public Cover(String small, String big) {
            this.small = small;
            this.big = big;
        }

        protected Cover(Parcel in) {
            small = in.readString();
            big = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(small);
            dest.writeString(big);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Cover> CREATOR = new Creator<Cover>() {
            @Override
            public Cover createFromParcel(Parcel in) {
                return new Cover(in);
            }

            @Override
            public Cover[] newArray(int size) {
                return new Cover[size];
            }
        };
    }

    protected Artist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        genres = in.createStringArrayList();
        tracks = in.readInt();
        albums = in.readInt();
        link = in.readString();
        description = in.readString();
        cover = in.readParcelable(Cover.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeStringList(genres);
        dest.writeInt(tracks);
        dest.writeInt(albums);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeParcelable(cover, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
