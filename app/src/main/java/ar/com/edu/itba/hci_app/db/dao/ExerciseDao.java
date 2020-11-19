package ar.com.edu.itba.hci_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.com.edu.itba.hci_app.db.entity.ExerciseEntity;

@Dao
public abstract class ExerciseDao {
    @Query("SELECT * FROM exercises WHERE cycleId = :id")
    public abstract LiveData<List<ExerciseEntity>> getCycleExercises(int id);

    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    public abstract LiveData<ExerciseEntity> getExerciseById(int exerciseId);

    @Query("SELECT * FROM exercises as exercises2 WHERE exercises2.id IN(SELECT exercises.id FROM cycles,routines,exercises WHERE cycles.routineId =routineId AND exercises.cycleId = cycles.id AND routines.id = :id)")
    public abstract LiveData<List<ExerciseEntity>> getRoutineExercises(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ExerciseEntity... exercises);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<ExerciseEntity> exercises);

    @Update
    public abstract void update(ExerciseEntity exerciseEntity);

    @Delete
    public abstract void delete(ExerciseEntity exerciseEntity);

    @Query("DELETE FROM exercises")
    public abstract void deleteAll();

    @Query("DELETE FROM EXERCISES WHERE id IN (SELECT id FROM exercises LIMIT :limit OFFSET :offset)")
    public abstract void delete(int limit, int offset);

    @Query("DELETE FROM exercises WHERE cycleId = :id")
    public abstract void deleteFromCycle(int id);

}
