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
        builder.id(bard.id());
        builder.name(bard.name());
        builder.description(bard.description());
        builder.albums(bard.albums());
        builder.tracks(bard.tracks());
        builder.bigImage(bard.bigImage());
        builder.smallImage(bard.smallImage());
        builder.link(bard.link());
        builder.genres(Collections.unmodifiableList(bard.genres()));
        return builder.build();
    }
}
