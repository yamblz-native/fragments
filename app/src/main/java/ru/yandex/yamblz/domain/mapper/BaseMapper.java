package ru.yandex.yamblz.domain.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 10.08.2016.
 */

public abstract class BaseMapper<Bad, Good> implements Mapper<Bad, Good> {
    @Override
    public List<Good> improove(List<Bad> bads) {
        List<Good> results = new ArrayList<Good>(bads.size());
        for (Bad b : bads)
            results.add(improove(b));
        return results;
    }
}
