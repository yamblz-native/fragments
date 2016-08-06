package ru.yandex.yamblz.artists;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.lib.ArtistModel;
import ru.yandex.yamblz.lib.ContentProviderContract;

import static ru.yandex.yamblz.lib.ArtistModel.cursorToModel;

//Singleton для получения данных об артистах из любого места приложения

public class DataSingleton {
    private static final String TAG = "DataSingleton";
    private static DataSingleton dataSingleton;
    private List<ArtistModel> artists;

    private DataSingleton(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean haveProvider;
        try {
            pm.getPackageInfo("ru.yandex.yamblz.provider", PackageManager.GET_ACTIVITIES);
            haveProvider = true;
        } catch (PackageManager.NameNotFoundException e) {
            haveProvider = false;
        }
        if (haveProvider) {
            artists = new ArrayList<>();
            Toast.makeText(context, "have content provider", Toast.LENGTH_LONG).show();
        } else {
            loadFromJson(context);
            Toast.makeText(context, "Load from Json", Toast.LENGTH_LONG).show();
        }
    }

    public LoadFromProviderTask loadFromContentProvider(Context context, Runnable onLoadDone) {
        return new LoadFromProviderTask(context, onLoadDone);
    }

    private void loadFromJson(Context context) {
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
        return artists != null && artists.size() != 0;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public class LoadFromProviderTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private Cursor cursor;
        private Runnable onLoadDone;

        public LoadFromProviderTask(Context context, Runnable onLoadDone) {
            this.context = context;
            this.onLoadDone = onLoadDone;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onLoadDone.run();
        }


        @Override
        protected Void doInBackground(Void... params) {
            cursor = context.getContentResolver().query(Uri.parse(ContentProviderContract.URL), null, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                Toast.makeText(context, "ContentProvider empty please load data", Toast.LENGTH_LONG).show();
                cursor.close();
            } else {
                artists.clear();
                while (cursor.moveToNext()) {
                    artists.add(cursorToModel(cursor));
                }
                cursor.close();

            }
            return null;
        }
    }
}
