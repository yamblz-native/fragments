package ru.yandex.yamblz.euv.shared.model;

public class Cover {
    private String small;
    private String big;

    public Cover(String small, String big) {
        this.small = small;
        this.big = big;
    }

    public Cover() { /* default constructor for the JSON parser */ }

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
    public String toString() {
        return "Cover{" +
                "small='" + small + '\'' +
                ", big='" + big + '\'' +
                '}';
    }
}
