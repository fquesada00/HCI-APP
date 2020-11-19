package ar.com.edu.itba.hci_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ar.com.edu.itba.hci_app.db.entity.CreatorEntity;
import ar.com.edu.itba.hci_app.db.entity.UserEntity;
import retrofit2.http.DELETE;

@Dao
public abstract class UserDao {

    @Query("SELECT * from users WHERE id = :id ")
    public abstract LiveData<UserEntity> getUserData(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(UserEntity... creators);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CreatorEntity... creators);

    @Delete
    public abstract void delete(UserEntity user);

    @Delete
    public abstract void delete(CreatorEntity creator);

     @Query("DELETE FROM users")
    public abstract void deleteUsersAll();

     @Query("DELETE FROM creators")
    public abstract void deleteCreatorsAll();

}
