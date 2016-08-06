package ru.yandex.yamblz.lib;


public interface ContentProviderContract {
    String AUTHORITY = "ru.yandex.yamblz.db.MyContentProvider";
    String URL = "content://" + AUTHORITY + "/artists";
}
