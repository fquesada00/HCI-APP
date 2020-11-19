package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "users",indices = {@Index("id")},primaryKeys = "id")
public class UserEntity extends UserAbstractEntity{

    @ColumnInfo(name = "fullName")
    public String fullName;
    @ColumnInfo(name = "birthdate")
    public Date birthdate;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "phone")
    public String phone;
    @ColumnInfo(name = "verified")
    public Boolean verified;
    @ColumnInfo(name = "deleted")
    public Boolean deleted;

    public UserEntity(@NonNull Integer id, String username, String fullName, String gender, Date birthdate, String email, String phone, String avatarUrl, Date dateCreated, Date dateLastActive, Boolean verified, Boolean deleted) {
        super(id,username,gender,avatarUrl,dateCreated,dateLastActive);
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.verified = verified;
        this.deleted = deleted;
    }
}
