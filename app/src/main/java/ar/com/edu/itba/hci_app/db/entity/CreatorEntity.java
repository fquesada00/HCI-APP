package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "creators",indices = {@Index("id")},primaryKeys = "id")
public class CreatorEntity extends UserAbstractEntity{

    public CreatorEntity(@NonNull Integer id, String username, String gender, String avatarUrl, Date dateCreated, Date dateLastActive) {
        super(id, username, gender, avatarUrl, dateCreated, dateLastActive);
    }
}
