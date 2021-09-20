package com.example.diplomska;

import android.provider.BaseColumns;

public class translateExercise {

    private translateExercise() {
    }
    public static final class TranslateEntry implements BaseColumns {
        public static final String TABLE_NAME = "groceryList";
        public static final String COLUMN_SENTENCE = "name";
        public static final String COLUMN_TRANSLATION = "translation";
        public static final String COLUMN_ALT_TRANSLATION = "alternativeTranslation";
    }

}
