package ru.yandex.yamblz.di;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.yamblz.domain.datasources.network.ApiConstants;
import ru.yandex.yamblz.domain.datasources.network.ArtistDeserializer;
import ru.yandex.yamblz.domain.datasources.network.YandexService;
import ru.yandex.yamblz.model.Artist;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    YandexService provideYandexService() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.registerTypeAdapter(Artist.class, new ArtistDeserializer()).create();

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(YandexService.class);
    }

}
