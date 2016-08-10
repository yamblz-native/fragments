package ru.yandex.yamblz.domain.datasource;

import com.google.gson.Gson;

import java.util.List;

import ru.yandex.yamblz.domain.model.core.Bard;
import rx.Single;

/**
 * Created by Александр on 10.08.2016.
 */

public class JsonDataSource implements IDataSource {

    private final Gson gson;

    public JsonDataSource(Gson gson) {
        this.gson = gson;
    }


    @Override
    public Single<List<Bard>> getAllBards() {
        return null;
    }

    @Override
    public Single<Bard> getBardById(long idBard) {
        return null;
    }
}
