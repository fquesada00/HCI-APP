package ar.com.edu.itba.hci_app.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;



public class Routine implements Comparable<Routine>, Serializable {

    @Override
    public String toString() {
        return "Routine{" +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public Routine(String difficulty, Creator creator, Date dateCreated, Double averageRating, String name, boolean isPublic, int id, String detail, Category category) {
        this.difficulty = difficulty;
        this.creator = creator;
        this.dateCreated = dateCreated;
        this.averageRating = averageRating;
        this.name = name;
        this.isPublic = isPublic;
        this.id = id;
        this.detail = detail;
        this.category = category;
    }

    public Routine() {
        this.id = -1;
    }

    private String difficulty;

    private Creator creator;

    private Date dateCreated;

    private Double averageRating;

    private String name;

    private boolean isPublic;

    private int id;

    private String detail;

    private Category category;

    public String getDifficulty() {
        return difficulty;
    }

    public Creator getCreator() {
        return creator;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getName() {
        return name;
    }

    public boolean isIsPublic() {
        return isPublic;
    }

    public int getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return id == routine.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Routine o) {
        return Integer.compare(this.id, o.id);
    }
}

