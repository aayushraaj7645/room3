package com.example.room3;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.room3.User;
import com.example.room3.userDao;

@Database(entities = {User.class}, version = 2)
public abstract class appDatabase extends RoomDatabase {
    public abstract userDao userDao();

    // Define migration from version 1 to 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Example migration SQL:
            // database.execSQL("ALTER TABLE User ADD COLUMN newColumn TEXT");
        }
    };
}
