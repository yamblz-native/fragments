package ru.yandex.yamblz.homework.artists.interfaces;

/**
 * Created by platon on 08.08.2016.
 */
public interface ToolbarProvider
{
    void updateToolbar(String title, boolean hasBackButton);
    void updateToolbar(String title);
}
