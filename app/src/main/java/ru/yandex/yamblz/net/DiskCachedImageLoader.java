package ru.yandex.yamblz.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.Cache;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class DiskCachedImageLoader implements ImageLoader.ImageCache{
    private static final int MAX_CACHE_SIZE_IN_BYTES = 1024 * 1024 * 50;
    private static final int COMPRESS_QUALITY = 100;

    private final ExecutorService executor = newFixedThreadPool(10);
    private final DiskBasedCache cache;

    public DiskCachedImageLoader(File cacheDir){
        cache = new DiskBasedCache(cacheDir, MAX_CACHE_SIZE_IN_BYTES);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        executor.execute(() -> {
            Cache.Entry entry = new Cache.Entry();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, COMPRESS_QUALITY, stream);

            entry.data = stream.toByteArray();
            cache.put(url, entry);
        });
    }

    @Override
    public Bitmap getBitmap(String url) {
        Cache.Entry entry = cache.get(url);
        byte[] imageCache = new byte[]{};
        if(entry != null){
            imageCache = entry.data;
        }
        return BitmapFactory.decodeByteArray(imageCache , 0, imageCache .length);
    }
}
