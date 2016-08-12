package ru.yamblz.netizen.provider.contentprovider.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import java.util.List;

/**
 * Created by Александр on 12.08.2016.
 */
@AutoValue
public abstract class BdBard {

    public abstract long id();

    public abstract String name();

    public abstract List<String> genres();

    @Nullable
    public abstract Integer tracks();

    @Nullable
    public abstract Integer albums();

    @Nullable
    public abstract String link();

    @Nullable
    public abstract String description();

    @Nullable
    public abstract String bigImage();

    @Nullable
    public abstract String smallImage();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder name(String name);

        public abstract Builder genres(List<String> genres);

        public abstract Builder tracks(Integer tracks);

        public abstract Builder albums(Integer albums);

        public abstract Builder link(String link);

        public abstract Builder description(String description);

        public abstract Builder bigImage(String bigImage);

        public abstract Builder smallImage(String smallImage);

        public abstract BdBard build();
    }

    public static Builder toBuilder(){
        return new AutoValue_BdBard.Builder();
    }


}
