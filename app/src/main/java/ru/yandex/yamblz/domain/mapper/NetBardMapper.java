package ru.yandex.yamblz.domain.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.yandex.yamblz.domain.model.api.NetBard;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.core.Genre;

/**
 * Created by Александр on 10.08.2016.
 */

public class NetBardMapper extends BaseMapper<NetBard, Bard> {
    @Override
    public Bard improove(NetBard netBard) {
        List<Genre> genres = new ArrayList<>(netBard.getGenres().length);
        for (String s : netBard.getGenres())
            genres.add(Genre.create(s.hashCode(), s));

        return Bard.create(netBard.getId(), netBard.getName(), genres, netBard.getTracks(),
                netBard.getAlbums(), netBard.getLink(), netBard.getDescription(),
                netBard.getImages().getSmallImage(), netBard.getImages().getBigImage());
    }
}
