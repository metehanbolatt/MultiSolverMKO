package com.deu.multisolvermko.createlibrary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LibraryInterfaceDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Library> getAllLibrary();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLibrary(Library library);

    @Delete
    void deleteLibrary(Library library);

}
