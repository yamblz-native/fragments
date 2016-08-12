package ru.yandex.yamblz.domain.model.presentation;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by Александр on 06.08.2016.
 */
@AutoValue
public abstract class BardUI implements Parcelable {
    public abstract long id();
    public abstract String name();
    public abstract List<String> genres();
    public abstract int tracks();
    public abstract int albums();
    @Nullable public abstract String link();
    public abstract String description();
    public abstract String smallImage();
    public abstract String bigImage();

    public static BardUI create(long id, String name, List<String> genres, int tracks, int albums,
                                String link, String description, String smallImage, String bigImage){
        return new AutoValue_BardUI(id, name, genres, tracks, albums, link, description, smallImage, bigImage);
    }

    @AutoValue.Builder
    public interface  Builder {
        Builder id(long id);
        Builder name(String name);
        Builder genres(List<String> genres);
        Builder tracks(int tracks);Builder albums(int albums);
        Builder link(String link);
        Builder description(String description);
        Builder smallImage(String smallImage);
        Builder bigImage(String BigImage);
        BardUI build();
    }

    public static Builder builder() {
        return new AutoValue_BardUI.Builder();
    }

}
