package ru.yandex.yamblz.data_base;

import android.database.Cursor;

class DbUtils {
    public static Long getResultLongAndClose(Cursor c) {
        Long id = getResultLong(c);
        closeCursor(c);
        return id;
    }

    public static Long getResultLong(Cursor c) {
        if (c != null && c.moveToFirst()) {
            return c.isNull(0) ? null : c.getLong(0);
        }
        return null;
    }

    public static void closeCursor(Cursor c) {
        if (c != null && !c.isClosed()) {
            c.close();
        }
    }
}
