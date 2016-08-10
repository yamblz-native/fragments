package ru.yandex.yamblz.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cover implements Parcelable {


    private long id;
    private String small;
    private String big;

    public Cover() {
    }

    public Cover(String small, String big) {
        this.small = small;
        this.big = big;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cover)) return false;

        Cover cover = (Cover) o;

        if (small != null ? !small.equals(cover.small) : cover.small != null) return false;
        return big != null ? big.equals(cover.big) : cover.big == null;

    }

    @Override
    public int hashCode() {
        int result = small != null ? small.hashCode() : 0;
        result = 31 * result + (big != null ? big.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cover{" +
                "id=" + id +
                ", small='" + small + '\'' +
                ", big='" + big + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.small);
        dest.writeString(this.big);
    }

    protected Cover(Parcel in) {
        this.id = in.readLong();
        this.small = in.readString();
        this.big = in.readString();
    }

    public static final Creator<Cover> CREATOR = new Creator<Cover>() {
        @Override
        public Cover createFromParcel(Parcel source) {
            return new Cover(source);
        }

        @Override
        public Cover[] newArray(int size) {
            return new Cover[size];
        }
    };
}
