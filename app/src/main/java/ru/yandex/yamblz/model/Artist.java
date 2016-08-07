package ru.yandex.yamblz.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by Aleksandra on 06/08/16.
 */
@AutoValue
abstract public class Artist implements Parcelable {

    public static Builder builder() {
        return new AutoValue_Artist.Builder();
    }

    public static Artist create(long id, String name, List<String> genres, long tracks,
                                long albums, @Nullable String link, String description, Cover cover) {
        return builder()
                .id(id)
                .name(name)
                .genres(genres)
                .tracks(tracks)
                .albums(albums)
                .link(link)
                .description(description)
                .cover(cover)
                .build();

    }

    public abstract long id();

    public abstract String name();

    public abstract List<String> genres();

    public abstract long tracks();

    public abstract long albums();

    @Nullable
    public abstract String link();

    public abstract String description();

    public abstract Cover cover();


    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(long id);

        public abstract Builder name(String name);

        public abstract Builder genres(List<String> genres);

        public abstract Builder tracks(long tracks);

        public abstract Builder albums(long albums);

        public abstract Builder link(String link);

        public abstract Builder description(String description);

        public abstract Builder cover(Cover cover);

        public abstract Artist build();
    }
}
