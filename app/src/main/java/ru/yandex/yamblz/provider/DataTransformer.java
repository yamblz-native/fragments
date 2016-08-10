package ru.yandex.yamblz.provider;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.yandex.yamblz.singerscontracts.Singer;

/**
 * Transforms data from Cursor formed by some contract
 */
public interface DataTransformer {

    @NonNull
    List<Singer> toSingers(@Nullable Cursor cursor);

    @Nullable
    Singer toSinger(@Nullable Cursor cursor);

}
