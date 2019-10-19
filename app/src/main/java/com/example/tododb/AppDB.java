package com.example.tododb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract DaoTask DBDaoTask();
}
