package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "exercises",indices = {@Index("id")},primaryKeys = {"id"})
public class ExerciseEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "detail")
    public String detail;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "duration")
    public Integer duration;

    @ColumnInfo(name = "repetitions")
    public Integer repetitions;

    @ColumnInfo(name = "order")
    public Integer order;

    @Override
    public String toString() {
        return "ExerciseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", repetitions=" + repetitions +
                ", order=" + order +
                ", cycleId=" + cycleId +
                '}';
    }

    @ColumnInfo(name = "cycleId")
    public int cycleId;


    public ExerciseEntity(@NonNull Integer id, String name, String detail, String type, Integer duration, Integer repetitions, Integer order, Integer cycleId) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.duration = duration;
        this.repetitions = repetitions;
        this.order = order;
        this.cycleId = cycleId;
    }
}
