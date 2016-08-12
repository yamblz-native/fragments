package ru.yandex.yamblz.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SerG3z on 07.08.16.
 */

public class Cover implements Serializable {
    @SerializedName("small")
    private String small;
    @SerializedName("big")
    private String big;

    public String getSmall() {
        return small;
    }

    public String getBig() {
        return big;
    }

    public Cover(String small, String big) {
        this.small = small;
        this.big = big;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cover cover = (Cover) o;

        return small.equals(cover.small) && big.equals(cover.big);
    }

    @Override
    public int hashCode() {
        int result = small.hashCode();
        result = 31 * result + big.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "small='" + small + '\'' +
                ", big='" + big + '\'' +
                '}';
    }
}
