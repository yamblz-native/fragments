package ru.yandex.yamblz.domain.model.presentation;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Александр on 06.08.2016.
 */
@AutoValue
public abstract class GenreUI implements Parcelable {
    public abstract long id();
    public abstract String name();

    public static GenreUI create(long id, String name){
        return new AutoValue_GenreUI(id, name);
    }

    public static GenreUI.Builder builder() {
        return new AutoValue_GenreUI.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        abstract GenreUI.Builder id(long idGenre);
        abstract GenreUI.Builder name(String name);
        abstract GenreUI build();
    }
}
