package ar.com.edu.itba.hci_app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ar.com.edu.itba.hci_app.db.entity.CycleEntity;

@Dao
public abstract class CycleDao {
    @Query("SELECT * FROM cycles WHERE routineId = :id")
    public abstract LiveData<List<CycleEntity>> getRoutineCycles(int id);

    @Query("SELECT * FROM cycles WHERE routineId = :routineId AND id = :cycleId")
    public abstract LiveData<CycleEntity> getCycle(int routineId, int cycleId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CycleEntity ... cycles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<CycleEntity> cycles);

    @Update
    public abstract void update(CycleEntity cycle);

    @Delete
    public abstract void delete(CycleEntity cycle);

    @Query("DELETE FROM cycles")
    public abstract void deleteAll();

    @Query("DELETE FROM cycles WHERE routineId = :id")
    public abstract void deleteRoutineCycles(int id);





}
