package com.example.room3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface userDao {

    @Insert
    void insert(User users);

   /* @Query("SELECT EXISTS(SELECT 1 FROM user WHERE first_name = :fname LIMIT 1)")
    boolean isUserExists(String fname);*/

   @Query("SELECT EXISTS(SELECT * FROM User WHERE first_name = :firstName)")
    Boolean isUserExists(String firstName);
@Query("SELECT * FROM User")
List<User> getAll();

    @Query("DELETE FROM User WHERE first_name = :firstName")
     void deleteByName(String firstName) ;




}