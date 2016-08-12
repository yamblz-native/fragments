package ru.yandex.yamblz.domain.model.core;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by Александр on 06.08.2016.
 */
@AutoValue
public abstract class Bard {
    public abstract long id();
    public abstract String name();
    public abstract List<String> genres();
    public abstract int tracks();
    public abstract int albums();
    @Nullable public abstract String link();
    public abstract String description();
    public abstract String smallImage();
    public abstract String bigImage();

    public static Bard create(long id, String name, List<String> genres, int tracks, int albums,
                              String link, String description, String smallImage, String bigImage){
        return Bard.toBuilder().id(id)
                .genres(genres)
                .albums(albums)
                .tracks(tracks)
                .bigImage(bigImage)
                .smallImage(smallImage)
                .description(description)
                .link(link)
                .name(name).build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder name(String name);

        public abstract Builder genres(List<String> genres);

        public abstract Builder tracks(int tracks);

        public abstract Builder albums(int albums);

        public abstract Builder link(String link);

        public abstract Builder description(String description);

        public abstract Builder bigImage(String bigImage);

        public abstract Builder smallImage(String smallImage);

        public abstract Bard build();
    }

    public static Builder toBuilder(){
        return new AutoValue_Bard.Builder();
    }
}
