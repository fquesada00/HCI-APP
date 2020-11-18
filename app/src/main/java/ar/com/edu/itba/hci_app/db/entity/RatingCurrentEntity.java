package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;
@Entity(tableName = "currentRatings",indices = {@Index("id")},primaryKeys = {"id"})
public class RatingCurrentEntity extends RatingAbstractEntity {
    public RatingCurrentEntity(@NonNull Integer id, Date date, Double score, String review, RoutineAbstractEntity routine) {
        super(id, date, score, review, routine);
    }
}
