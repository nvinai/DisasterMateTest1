package com.example.calorietracker_v02;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsDAO {

    //TODO:possible issues here
    @Query("SELECT * FROM steps")
    List<Steps> getAll();

    @Query("SELECT * FROM steps WHERE steps LIKE :step AND " + "time LIKE :time LIMIT 1")
    Steps findByStepsAndTime(String step, String time);

    @Query("SELECT * FROM steps WHERE uid = :stepsId LIMIT 1")
    Steps findByID(int stepsId);

    @Insert
    void insertAll(Steps... stepss);

    @Insert
    long insert(Steps steps);

    @Delete
    void delete(Steps steps);

    @Update(onConflict = REPLACE)
    public void updateUsers(Steps... stepss);

    @Query("DELETE FROM steps")
    void deleteAll();
}
