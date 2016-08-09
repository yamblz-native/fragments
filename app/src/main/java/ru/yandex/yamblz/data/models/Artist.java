package ru.yandex.yamblz.data.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverColumn;
import com.pushtorefresh.storio.contentresolver.annotations.StorIOContentResolverType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.yandex.yamblz.utils.AppConfig;
import timber.log.Timber;

/**
 * Created by shmakova on 12.04.16.
 */
@StorIOContentResolverType(uri = AppConfig.ARTISTS_URI)
public class Artist implements Parcelable {
    @StorIOContentResolverColumn(name = "rowid", key = true)
    int id;
    @StorIOContentResolverColumn(name = "name")
    String name;
    private List<String> genres;
    private Cover cover;
    @StorIOContentResolverColumn(name = "tracks")
    int tracks;
    @StorIOContentResolverColumn(name = "albums")
    int albums;
    @StorIOContentResolverColumn(name = "description")
    String description;
    @StorIOContentResolverColumn(name = "link")
    String link;
    @StorIOContentResolverColumn(name = "cover_small")
    String coverSmall;
    @StorIOContentResolverColumn(name = "cover_big")
    String coverBig;
    @StorIOContentResolverColumn(name = "genres_list")
    String genresString;

    Artist() {
    }

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

    @Override
    public String toString() {
        return "Artist: " + name;
    }

    private String getCoverSmall() {
        return coverSmall;
    }

    private String getCoverBig() {
        return coverBig;
    }

    private String getGenresString() {
        return genresString;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    private void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public static Artist convertArtistFields(Artist artist) {
        Timber.d(artist.getName());
        List<String> genres = new ArrayList<>(Collections.singletonList(artist.getGenresString()));

        Cover cover = new Cover(
                artist.getCoverSmall(),
                artist.getCoverBig()
        );

        artist.setCover(cover);
        artist.setGenres(genres);

        return artist;
    }
}
