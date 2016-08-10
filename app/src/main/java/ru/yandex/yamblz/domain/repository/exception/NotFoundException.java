package ru.yandex.yamblz.domain.repository.exception;

/**
 * Created by Александр on 05.08.2016.
 */

public class NotFoundException extends RuntimeException {
    public NotFoundException(String detailMessage) {
        super(detailMessage);
    }
}
