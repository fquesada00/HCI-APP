package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "ratings",indices = {@Index("id")},primaryKeys = {"id"})
public class RatingEntity extends RatingAbstractEntity{

    public RatingEntity(@NonNull Integer id, Date date, Double score, String review, RoutineAbstractEntity routine) {
        super(id, date, score, review, routine);
    }
}
