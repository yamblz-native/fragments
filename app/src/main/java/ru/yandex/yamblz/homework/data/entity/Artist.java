package ru.yandex.yamblz.homework.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by postnov on 12.04.2016.
 */
public class Artist implements Parcelable
{
    private int id;
    private String name;
    private String genres;
    private int tracks;
    private int albums;
    private String desc;
    private String coverSmall;
    private String coverBig;

    protected Artist(int id, String name, String genres, int tracks,
                     int albums, String desc, String coverSmall, String coverBig)
    {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.desc = desc;
        this.coverSmall = coverSmall;
        this.coverBig = coverBig;
    }

    protected Artist(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        genres = in.readString();
        tracks = in.readInt();
        albums = in.readInt();
        desc = in.readString();
        coverSmall = in.readString();
        coverBig = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>()
    {
        @Override
        public Artist createFromParcel(Parcel in)
        {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size)
        {
            return new Artist[size];
        }
    };

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getGenres()
    {
        return genres;
    }

    public int getTracks()
    {
        return tracks;
    }

    public int getAlbums()
    {
        return albums;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getCoverSmall()
    {
        return coverSmall;
    }

    public String getCoverBig()
    {
        return coverBig;
    }

    public static Builder Builder()
    {
        return new Builder();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(genres);
        parcel.writeInt(tracks);
        parcel.writeInt(albums);
        parcel.writeString(desc);
        parcel.writeString(coverSmall);
        parcel.writeString(coverBig);
    }

    public static class Builder
    {
        private int id;
        private String name;
        private String genres;
        private int tracks;
        private int albums;
        private String desc;
        private String coverSmall;
        private String coverBig;

        public Builder setId(int id)
        {
            this.id = id;
            return this;
        }

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setGenres(String genres)
        {
            this.genres = genres;
            return this;
        }

        public Builder setTracks(int tracks)
        {
            this.tracks = tracks;
            return this;
        }

        public Builder setAlbums(int albums)
        {
            this.albums = albums;
            return this;
        }

        public Builder setDesc(String desc)
        {
            this.desc = desc;
            return this;
        }

        public Builder setCoverSmall(String cover)
        {
            coverSmall = cover;
            return this;
        }

        public Builder setCoverBig(String cover)
        {
            coverBig = cover;
            return this;
        }

        public Artist build()
        {
            return new Artist(id, name, genres, tracks, albums, desc, coverSmall, coverBig);
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        if (tracks != artist.tracks) return false;
        if (albums != artist.albums) return false;
        if (!name.equals(artist.name)) return false;
        if (!genres.equals(artist.genres)) return false;
        if (!desc.equals(artist.desc)) return false;
        if (!coverSmall.equals(artist.coverSmall)) return false;

        return coverBig.equals(artist.coverBig);

    }

    @Override
    public int hashCode()
    {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + genres.hashCode();
        result = 31 * result + tracks;
        result = 31 * result + albums;
        result = 31 * result + desc.hashCode();
        result = 31 * result + coverSmall.hashCode();
        result = 31 * result + coverBig.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        builder.append("Artist { ");
        builder.append("id: ");
        builder.append(id);
        builder.append(" name: ");
        builder.append(name);
        builder.append(" genres: ");
        builder.append(genres);
        builder.append(" albums: ");
        builder.append(albums);
        builder.append(" tracks: ");
        builder.append(tracks);
        builder.append(" }");

        return builder.toString();
    }
}
