package ru.yandex.yamblz.domain.mapper;

import java.util.Collection;
import java.util.List;

/**
 * Created by Александр on 10.08.2016.
 */

public interface Mapper<Bad, Good> {
    Good improove(Bad bad);
    List<Good> improove(List<Bad> bads);
}
