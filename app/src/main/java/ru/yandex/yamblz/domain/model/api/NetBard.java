package ru.yandex.yamblz.domain.model.api;


import com.google.gson.annotations.SerializedName;


/**
 * Created by Александр on 10.08.2016.
 */

public class NetBard {
    @SerializedName(ApiConst.FIELD_ID)
    private long id;
    @SerializedName(ApiConst.FIELD_NAME)
    private String name;
    @SerializedName(ApiConst.FIELD_GENRES)
    private String[] genres;
    @SerializedName(ApiConst.FIELD_TRACKS)
    private int tracks;
    @SerializedName(ApiConst.FIELD_ALBUMS)
    private int albums;
    @SerializedName(ApiConst.FIELD_LINK)
    private String link;
    @SerializedName(ApiConst.FIELD_DESCRIPTION)
    private String description;
    @SerializedName(ApiConst.FIELD_PHOTOS)
    private NetImages images;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getGenres() {
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

    public NetImages getImages() {
        return images;
    }
}
