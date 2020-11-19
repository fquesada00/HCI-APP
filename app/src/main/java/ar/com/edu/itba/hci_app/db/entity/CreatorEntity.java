package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "creators",indices = {@Index("id")},primaryKeys = "id")
public class CreatorEntity {

    @NonNull
    @ColumnInfo(name = "id")
    public Integer id;
    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "gender")
    public String gender;
    @ColumnInfo(name = "avatarUrl")
    public String avatarUrl;
    @ColumnInfo(name = "dateCreated")
    public Date dateCreated;
    @ColumnInfo(name = "dateLastActive")
    public Date dateLastActive;

    public CreatorEntity(@NonNull Integer id, String username, String gender, String avatarUrl, Date dateCreated, Date dateLastActive) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
    }
}
