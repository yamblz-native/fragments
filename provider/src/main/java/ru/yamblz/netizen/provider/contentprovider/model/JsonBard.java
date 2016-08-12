package ru.yamblz.netizen.provider.contentprovider.model;


import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * Created by Александр on 10.08.2016.
 */
@AutoValue
public abstract class JsonBard {
    @SerializedName(JsonConst.FIELD_ID)
    public abstract long id();
    @SerializedName(JsonConst.FIELD_NAME)
    public abstract String name();
    @Nullable
    @SerializedName(JsonConst.FIELD_GENRES)
    public abstract List<String> genres();
    @SerializedName(JsonConst.FIELD_TRACKS)
    public abstract int tracks();
    @SerializedName(JsonConst.FIELD_ALBUMS)
    public abstract int albums();
    @Nullable
    @SerializedName(JsonConst.FIELD_LINK)
    public abstract String link();
    @Nullable
    @SerializedName(JsonConst.FIELD_DESCRIPTION)
    public abstract String description();

    @SerializedName(JsonConst.FIELD_PHOTOS)
    public abstract JsonImages images();


    public static TypeAdapter<JsonBard> typeAdapter(Gson gson) {
        return new AutoValue_JsonBard.GsonTypeAdapter(gson);
    }
}
