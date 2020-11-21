package ar.com.edu.itba.hci_app.domain;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Creator implements Serializable {

    private Date dateLastActive;

    public Creator(String gender, int id, String username) {
        this.gender = gender;
        this.id = id;
        this.username = username;
    }

    public Creator(Date dateLastActive, Date dateCreated, String gender, String avatarUrl, int id, String username) {
        this.dateLastActive = dateLastActive;
        this.dateCreated = dateCreated;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.id = id;
        this.username = username;
    }

    private Date dateCreated;

    private String gender;

    private String avatarUrl;

    private int id;

    private String username;


    public Date getDateLastActive(){
        return dateLastActive;
    }

    public Date getDateCreated(){
        return dateCreated;
    }

    public String getGender(){
        return gender;
    }

    public String getAvatarUrl(){
        return avatarUrl;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creator creator = (Creator) o;
        return id == creator.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}