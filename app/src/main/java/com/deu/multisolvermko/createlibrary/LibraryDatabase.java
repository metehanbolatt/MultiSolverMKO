package com.deu.multisolvermko.createlibrary;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Library.class, version = 1,exportSchema = false)
public abstract class LibraryDatabase extends RoomDatabase {

    private static LibraryDatabase libraryDatabase;

    public static synchronized LibraryDatabase getDatabase(Context context){
        if (libraryDatabase == null){
            libraryDatabase = Room.databaseBuilder(context, LibraryDatabase.class,"notes_db").build();
        }
        return libraryDatabase;
    }

    public abstract LibraryInterfaceDao libraryInterfaceDao();

}