package ar.com.edu.itba.hci_app.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import java.util.Date;

@Entity(tableName = "users",indices = {@Index("id")},primaryKeys = "id")
public class UserEntity {
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
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.verified = verified;
        this.deleted = deleted;
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
    }
}
