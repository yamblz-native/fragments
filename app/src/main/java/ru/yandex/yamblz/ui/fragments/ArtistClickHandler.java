package ru.yandex.yamblz.ui.fragments;

import java.io.Serializable;

import ru.yandex.yamblz.model.Artist;


public interface ArtistClickHandler extends Serializable {
    void artistClicked(Artist artist);
}
