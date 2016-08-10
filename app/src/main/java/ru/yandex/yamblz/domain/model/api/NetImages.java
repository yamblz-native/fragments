package ru.yandex.yamblz.domain.model.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Александр on 10.08.2016.
 */

public  class NetImages {
    @SerializedName(ApiConst.FIELD_SMALL_PHOTO)
    private String smallImage;
    @SerializedName(ApiConst.FIELD_BIG_PHOTO)
    private String bigImage;

    public String getSmallImage() {
        return smallImage;
    }

    public String getBigImage() {
        return bigImage;
    }
}
