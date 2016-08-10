package ru.yandex.yamblz.domain.datasource;

import java.util.List;

import retrofit2.http.GET;
import ru.yandex.yamblz.domain.model.api.ApiConst;
import ru.yandex.yamblz.domain.model.api.NetBard;
import rx.Observable;

public interface BardService {
    @GET(ApiConst.METHOD_BARDS)
    Observable<List<NetBard>> getBards();
}