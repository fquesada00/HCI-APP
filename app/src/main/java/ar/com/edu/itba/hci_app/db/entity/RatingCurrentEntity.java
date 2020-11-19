package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;
@Entity(tableName = "currentRatings",indices = {@Index("id")},primaryKeys = {"id"})
public class RatingCurrentEntity {
    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;
    @ColumnInfo(name = "date" )
    public Date date;
    @ColumnInfo(name = "score")
    public Double score;
    @ColumnInfo(name = "review")
    public String review;
    @Embedded(prefix = "routine_")
    public RoutineCurrentEntity routine;

    public RatingCurrentEntity(@NonNull Integer id, Date date, Double score, String review, RoutineCurrentEntity routine) {
        this.id = id;
        this.date = date;
        this.score = score;
        this.review = review;
        this.routine = routine;
    }
}
