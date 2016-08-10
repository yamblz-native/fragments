package ru.yandex.yamblz.domain.model.core;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by Александр on 06.08.2016.
 */
@AutoValue
public abstract class Bard {
    public abstract long getId();
    public abstract String getName();
    public abstract List<Genre> getGenres();
    public abstract int getTracks();
    public abstract int getAlbums();
    public abstract String getLink();
    public abstract String getDescription();
    public abstract String getSmallImage();
    public abstract String getBigImage();

    public static Bard create(long id, String name, List<Genre> genres, int tracks, int albums,
                              String link, String description, String smallImage, String bigImage){
        return new AutoValue_Bard(id, name, genres, tracks, albums, link, description, smallImage, bigImage);
    }
}
