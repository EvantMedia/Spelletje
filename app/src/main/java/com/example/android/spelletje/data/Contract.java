package com.example.android.spelletje.data;

import android.provider.BaseColumns;

public final class Contract {
    public static final class spelEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "Spelers";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RANK = "rank";
    }
}
