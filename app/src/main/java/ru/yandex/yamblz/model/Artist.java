package ru.yandex.yamblz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SerG3z on 11.07.16.
 */
public class Artist implements Parcelable {

    private int id;
    private String name;
    private String link;
    private int tracks;
    private int albums;
    private String coverBig;
    private String coverSmall;
    private String description;
    private String genresString;

    public Artist() {
    }

    public Artist(int id, String name, String link, int tracks, int albums, String coverBig, String coverSmall, String description, String genresString) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.tracks = tracks;
        this.albums = albums;
        this.coverBig = coverBig;
        this.coverSmall = coverSmall;
        this.description = description;
        this.genresString = genresString;
    }

    protected Artist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        link = in.readString();
        tracks = in.readInt();
        albums = in.readInt();
        coverBig = in.readString();
        coverSmall = in.readString();
        description = in.readString();
        genresString = in.readString();
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

    public String getDescription() {
        return description;
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

    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(link);
        dest.writeInt(tracks);
        dest.writeInt(albums);
        dest.writeString(coverBig);
        dest.writeString(coverSmall);
        dest.writeString(description);
        dest.writeString(genresString);
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public String getGenresString() {
        return genresString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        if (tracks != artist.tracks) return false;
        if (albums != artist.albums) return false;
        if (!name.equals(artist.name)) return false;
        if (!link.equals(artist.link)) return false;
        if (!coverBig.equals(artist.coverBig)) return false;
        if (!coverSmall.equals(artist.coverSmall)) return false;
        if (!description.equals(artist.description)) return false;
        return genresString.equals(artist.genresString);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + tracks;
        result = 31 * result + albums;
        result = 31 * result + coverBig.hashCode();
        result = 31 * result + coverSmall.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + genresString.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", tracks=" + tracks +
                ", albums=" + albums +
                ", coverBig='" + coverBig + '\'' +
                ", coverSmall='" + coverSmall + '\'' +
                ", description='" + description + '\'' +
                ", genresString='" + genresString + '\'' +
                '}';
    }
}


