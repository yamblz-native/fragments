package ru.yamblz.netizen.provider.contentprovider.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Александр on 10.08.2016.
 */
@AutoValue
public abstract class JsonImages {
    @Nullable
    @SerializedName(JsonConst.FIELD_SMALL_PHOTO)
    public abstract String smallImage();
    @Nullable
    @SerializedName(JsonConst.FIELD_BIG_PHOTO)
    public abstract String bigImage();


    public static TypeAdapter<JsonImages> typeAdapter(Gson gson) {
        return new AutoValue_JsonImages.GsonTypeAdapter(gson);
    }
}
