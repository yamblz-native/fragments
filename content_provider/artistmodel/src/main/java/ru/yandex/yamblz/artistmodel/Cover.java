package ru.yandex.yamblz.artistmodel;

import java.io.Serializable;

/**
 * Created by root on 8/8/16.
 */
public class Cover implements Serializable {

    private String big;
    private String small;

    public Cover() {

    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
