package ru.yandex.yamblz.homework.base;

/**
 * Created by platon on 06.08.2016.
 */
public interface BasePresenter<V>
{
    void bind(V view);
    void unbind();
    void unsubscribe();
}
