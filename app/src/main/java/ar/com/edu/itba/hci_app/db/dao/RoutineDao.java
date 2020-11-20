package ar.com.edu.itba.hci_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.com.edu.itba.hci_app.db.entity.RatingCurrentEntity;
import ar.com.edu.itba.hci_app.db.entity.RatingEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineCurrentEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineFavEntity;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.api.model.RatingModel;

@Dao
public abstract class RoutineDao {

    @Query("SELECT * FROM routines")
    public abstract LiveData<List<RoutineEntity>> getRoutines();

    @Query("SELECT * FROM routines WHERE creator_id = :id")
    public abstract LiveData<List<RoutineEntity>> getUserRoutines(int id);

    @Query("SELECT * FROM currentRoutines")
    public abstract LiveData<List<RoutineCurrentEntity>> getCurrentRoutines();

    @Query("SELECT * FROM routinesFav")
    public abstract LiveData<List<RoutineFavEntity>> getUserFavs();

    @Query("SELECT * FROM ratings WHERE routine_creator_id = :id")
    public abstract LiveData<List<RatingEntity>> getUserRating(int id);

    @Query("SELECT * FROM routines WHERE id = :id")
    public abstract LiveData<RoutineEntity> getRoutine(int id);

    @Query("SELECT *FROM routinesFav WHERE id = :id")
    public abstract LiveData<RoutineFavEntity> getFavRoutine(int id);

    @Query("SELECT * FROM ratings WHERE routine_id = :id")
    public abstract LiveData<List<RatingEntity>> getRoutineRating(int id);

    @Query("SELECT * FROM ratings WHERE id =:id")
    public abstract LiveData<RatingEntity> getRatingById(int id);

    @Query("SELECT * FROM currentRatings")
    public abstract LiveData<List<RatingCurrentEntity>> getCurrentRatings();

    @Query("SELECT * FROM routines WHERE id IN(SELECT routines2.id FROM routines as routines2,categories WHERE routines2.category_id = categories.id AND categories.id = :id)")
    public abstract LiveData<List<RoutineEntity>> getRoutineByCategory(int id);

    @Query("SELECT * FROM routines LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineEntity>> getRoutines(int limit, int offset);

    @Query("SELECT * FROM routinesFav LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineFavEntity>> getUserFavs(int limit, int offset);

    @Query("SELECT * FROM routines WHERE creator_id = :id LIMIT :limit OFFSET :offset")
    public abstract LiveData<List<RoutineEntity>> getUserRoutines(int id,int limit, int offset);

    @Query("SELECT * FROM routines WHERE name LIKE :query || '%'")
    public abstract LiveData<List<RoutineEntity>> searchRoutines(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRoutine(RoutineEntity... routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRoutine(List<RoutineEntity> routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCurrentRoutine(RoutineCurrentEntity ... routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCurrentRoutine(List<RoutineCurrentEntity> routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRoutineFav(RoutineFavEntity... routinesFav);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRoutineFav(List<RoutineFavEntity> routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRating(RatingEntity... ratings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRating(List<RatingEntity> routines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCurrentRating(List<RatingCurrentEntity> routines);

    @Update
    public abstract void update(RoutineEntity routine);

    @Update
    public abstract void update(RoutineCurrentEntity routine);

    @Update
    public abstract void update(RoutineFavEntity routine);

    @Delete
    public abstract void delete(RoutineEntity routine);

    @Delete
    public abstract void delete(RatingEntity rating);

    @Delete
    public abstract void delete(RatingCurrentEntity ratingCurrentEntity);

    @Delete
    public abstract void delete(RoutineCurrentEntity routine);

    @Delete
    public abstract void delete(RoutineFavEntity routine);

    @Query("DELETE FROM routines")
    public abstract void deleteRoutines();

    @Query("DELETE FROM routines WHERE id in(SELECT id from routines LIMIT :limit OFFSET :offset)")
    public abstract void deleteRoutines(int limit, int offset);

    @Query("DELETE FROM routinesFav")
    public abstract void deleteFavRoutines();

    @Query("DELETE FROM currentRoutines")
    public abstract void deleteCurrentUserRoutines();

    @Query("DELETE FROM ratings")
    public abstract void deleteAllRatings();

    @Query("DELETE FROM currentRatings")
    public abstract void deleteCurrentRatings();

    @Query("DELETE FROM routines WHERE creator_id  = :id")
    public abstract void deleteRoutineFromCreator(int id);

    @Query("DELETE FROM ratings WHERE routine_id = :id")
    public abstract void deleteRoutineRatings(int id);

    @Query("DELETE FROM routinesFav WHERE id = :id")
    public abstract void deleteRoutineFav(int id);




}
