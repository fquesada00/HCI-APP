package ar.com.edu.itba.hci_app.network.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Creator {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("gender\"")
    @Expose
    private String gender;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("dateCreated")
    @Expose
    private int dateCreated;
    @SerializedName("dateLastActive")
    @Expose
    private int dateLastActive;


    public Creator() {
    }


    public Creator(int id, String username, String gender, String avatarUrl, int dateCreated, int dateLastActive) {
        super();
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.dateCreated = dateCreated;
        this.dateLastActive = dateLastActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(int dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getDateLastActive() {
        return dateLastActive;
    }

    public void setDateLastActive(int dateLastActive) {
        this.dateLastActive = dateLastActive;
    }

}