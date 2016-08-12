package ru.yamblz.netizen.provider.contentprovider.model;

/**
 * Created by Александр on 03.04.2016.
 */
public class JsonConst {

    private JsonConst() {
        throw new RuntimeException("not have instance");
    }

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_GENRES = "genres";
    public static final String FIELD_TRACKS = "tracks";
    public static final String FIELD_ALBUMS = "albums";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_PHOTOS = "cover";
    public static final String FIELD_SMALL_PHOTO = "small";
    public static final String FIELD_BIG_PHOTO = "big";
}
