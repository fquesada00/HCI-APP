package ar.com.edu.itba.hci_app.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ar.com.edu.itba.hci_app.db.dao.CategoryDao;
import ar.com.edu.itba.hci_app.db.dao.CycleDao;
import ar.com.edu.itba.hci_app.db.dao.ExerciseDao;
import ar.com.edu.itba.hci_app.db.dao.RoutineDao;
import ar.com.edu.itba.hci_app.db.dao.UserDao;
import ar.com.edu.itba.hci_app.db.entity.CategoryEntity;
import ar.com.edu.itba.hci_app.db.entity.CreatorEntity;
import ar.com.edu.itba.hci_app.db.entity.CycleEntity;
import ar.com.edu.itba.hci_app.db.entity.ExerciseEntity;
import ar.com.edu.itba.hci_app.db.entity.RatingCurrentEntity;
import ar.com.edu.itba.hci_app.db.entity.RatingEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineCurrentEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineFavEntity;
import ar.com.edu.itba.hci_app.db.entity.UserEntity;
import ar.com.edu.itba.hci_app.domain.Routine;

@TypeConverters({Converters.class})
@Database(entities = {RoutineEntity.class, CycleEntity.class,
        ExerciseEntity.class, RatingEntity.class, UserEntity.class,
        RoutineFavEntity.class, CategoryEntity.class, RoutineCurrentEntity.class,
        RatingCurrentEntity.class, CreatorEntity.class}, exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    abstract public RoutineDao routineDao();

    abstract public ExerciseDao exerciseDao();

    abstract public UserDao userDao();

    abstract public CycleDao cycleDao();

    abstract public CategoryDao categoryDao();
}