package ru.yandex.yamblz.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Aleksandra on 07/08/16.
 */

@AutoValue
abstract public class Cover implements Parcelable {

    public static Builder builder() {
        return new AutoValue_Cover.Builder();
    }

    public static Cover create(String smallCover, String bigCover) {
        return builder()
                .smallCover(smallCover)
                .bigCover(bigCover)
                .build();
    }

    public abstract String smallCover();

    public abstract String bigCover();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder smallCover(String smallCover);

        public abstract Builder bigCover(String smallCover);

        public abstract Cover build();
    }

}
