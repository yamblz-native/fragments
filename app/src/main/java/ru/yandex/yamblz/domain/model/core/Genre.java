package ru.yandex.yamblz.domain.model.core;

import com.google.auto.value.AutoValue;


/**
 * Created by Александр on 06.08.2016.
 */
@AutoValue
public abstract class Genre {
    public abstract long id();
    public abstract String name();

    public static Genre create(long id, String name){
        return AutoValue_Genre.builder().id(id).name(name).build();
    }

    public static Builder builder() {
        return new AutoValue_Genre.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        abstract Builder id(long idGenre);
        abstract Builder name(String name);
        abstract Genre build();
    }
}
