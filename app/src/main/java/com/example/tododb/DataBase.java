package com.example.tododb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

public class DataBase {
    private Context context;
    private static DataBase db;

    private AppDB appDB;

    private DataBase(Context context) {
        this.context = context;

        appDB = Room.databaseBuilder(context, AppDB.class, "ToDo").build();
    }

    public static synchronized DataBase getInstance(Context context){
        if(db == null){
            db = new DataBase(context);
        }
        return db;
    }

    public AppDB getAppDB(){
        return appDB;
    }
}
