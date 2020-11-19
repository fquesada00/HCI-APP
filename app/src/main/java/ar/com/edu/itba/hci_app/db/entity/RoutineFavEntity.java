package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "routinesFav",indices = {@Index("id")},primaryKeys = {"id"})
public class RoutineFavEntity {
    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "detail")
    public String detail;
    @ColumnInfo(name = "dateCreated")
    public Date dateCreated;
    @ColumnInfo(name = "averageRating")
    public Double averageRating;
    @ColumnInfo(name = "isPublic")
    public Boolean isPublic;
    @ColumnInfo(name = "difficulty")
    public String difficulty;
    @Embedded(prefix = "category_")
    public CategoryEntity category;
    @Embedded(prefix = "creator_")
    public CreatorEntity creator;

    public RoutineFavEntity(@NonNull Integer id, String name, String detail, Date dateCreated, Double averageRating, Boolean isPublic, String difficulty, CategoryEntity category, CreatorEntity creator) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.isPublic = isPublic;
        this.difficulty = difficulty;
        this.category = category;
        this.creator = creator;
    }
}
