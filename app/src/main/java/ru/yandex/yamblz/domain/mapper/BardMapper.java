package ru.yandex.yamblz.domain.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.core.Genre;
import ru.yandex.yamblz.domain.model.presentation.BardUI;

/**
 * Created by Александр on 10.08.2016.
 */

public class BardMapper extends BaseMapper<Bard, BardUI> {
    @Override
    public BardUI improove(Bard bard) {
        BardUI.Builder builder = BardUI.builder();
        builder.id(bard.getId());
        builder.name(bard.getName());
        builder.description(bard.getDescription());
        builder.albums(bard.getAlbums());
        builder.tracks(bard.getTracks());
        builder.bigImage(bard.getBigImage());
        builder.smallImage(bard.getSmallImage());
        builder.link(bard.getLink());
        List<String> genres = new ArrayList<>(bard.getGenres().size());
        for (Genre g: bard.getGenres())
            genres.add(g.name());
        builder.genres(Collections.unmodifiableList(genres));
        return builder.build();
    }
}
