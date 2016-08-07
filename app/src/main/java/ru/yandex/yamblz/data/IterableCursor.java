package ru.yandex.yamblz.data;

/**
 * Created by shmakova on 07.08.16.
 */

import android.database.Cursor;

import java.util.Iterator;

public class IterableCursor implements Iterable<Cursor> {
    private Cursor cursor;

    public IterableCursor(Cursor cursor) {
        this.cursor = cursor;
        this.cursor.moveToPosition(-1);
    }

    @Override
    public Iterator<Cursor> iterator() {
        return new Iterator<Cursor>() {
            @Override
            public boolean hasNext() {
                if (cursor.isClosed() && !cursor.moveToNext()) {
                    cursor.close();
                }
                return !cursor.isClosed() && cursor.moveToNext();
            }

            @Override
            public Cursor next() {
                return cursor;
            }

            @Override
            public void remove() {
            }
        };
    }
}