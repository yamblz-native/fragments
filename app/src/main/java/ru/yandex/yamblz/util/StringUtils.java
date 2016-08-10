package ru.yandex.yamblz.util;

import android.content.Context;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artistmodel.Artist;

/**
 * Created by root on 8/10/16.
 */
public class StringUtils {

    public static String getGenresString(String[] array) {

        StringBuilder sb = new StringBuilder();
        for(String genre : array) {
            sb.append(' ');
            sb.append(genre);
            sb.append(',');
        }

        String genres = null;
        if(array.length > 0) {
            genres = sb.substring(1, sb.length() - 1);
        } else {
            genres = sb.toString();
        }
        return genres;
    }

    public static String getCounterInfoString(Context context, Artist artist) {
        StringBuilder counterBuilder = new StringBuilder();

        counterBuilder.append(context.getResources().getQuantityString(R.plurals.albums, artist.getAlbums(), artist.getAlbums()));
        counterBuilder.append(", ");
        counterBuilder.append(context.getResources().getQuantityString(R.plurals.tracks, artist.getTracks(), artist.getTracks()));

        return counterBuilder.toString();
    }

}
