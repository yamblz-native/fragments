package ru.yandex.yamblz.artists;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

//Singleton для получения данных об артистах из любого места приложения

public class DataSingleton {
    private static final String TAG = "DataSingleton";
    private static DataSingleton dataSingleton;
    private List<ArtistModel> artists;

    private DataSingleton(Context context) {
        StringBuilder buf = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStream json = context.getAssets().open("artists.json");
            reader = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;
            while ((str = reader.readLine()) != null) {
                buf.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new GsonBuilder().create();
        artists = gson.fromJson(buf.toString(), new TypeToken<List<ArtistModel>>() {
        }.getType());
    }

    public static void init(Context context) {
        if (dataSingleton != null) {
            throw new RuntimeException("singleton must be init once");
        } else {
            dataSingleton = new DataSingleton(context);
        }
    }

    public static DataSingleton get() {
        return dataSingleton;
    }

    public static void dispose() {
        dataSingleton = null;
    }

    public boolean hasData() {
        return artists != null;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }
}
