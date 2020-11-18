package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import java.util.Date;

public abstract class RatingAbstractEntity {
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
    public RoutineAbstractEntity routine;

    public RatingAbstractEntity(@NonNull Integer id, Date date, Double score, String review, RoutineAbstractEntity routine) {
        this.id = id;
        this.date = date;
        this.score = score;
        this.review = review;
        this.routine = routine;
    }
}
