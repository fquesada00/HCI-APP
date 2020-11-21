package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "cycles",indices = {@Index("id")},primaryKeys = {"id"})
public class CycleEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "detail")
    public String detail;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "order")
    public Integer order;


    @ColumnInfo(name = "repetitions")
    public Integer repetitions;

    @ColumnInfo(name = "routineId")
    public Integer routine;

    public CycleEntity(@NonNull Integer id, String name, String detail, String type, Integer order, Integer repetitions, int routine) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.type = type;
        this.order = order;
        this.repetitions = repetitions;
        this.routine = routine;
    }

    @Override
    public String toString() {
        return "CycleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", type='" + type + '\'' +
                ", order=" + order +
                ", repetitions=" + repetitions +
                ", routine=" + routine +
                '}';
    }
}
