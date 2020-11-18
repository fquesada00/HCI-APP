package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "routinesFav",indices = {@Index("id")},primaryKeys = {"id"})
public class RoutineFavEntity extends RoutineAbstractEntity {
    public RoutineFavEntity(@NonNull Integer id, String name, String detail, Date dateCreated, Double averageRating, Boolean isPublic, String difficulty, CategoryEntity category, CreatorEntity creator) {
        super(id, name, detail, dateCreated, averageRating, isPublic, difficulty, category, creator);
    }
}
