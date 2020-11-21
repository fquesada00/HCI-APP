package ar.com.edu.itba.hci_app.domain;


import java.util.Date;
import java.util.Objects;

import ar.com.edu.itba.hci_app.network.api.model.RoutineModel;

public class Rating {

    public Rating(Double score, String review)
    {
        this.score = score;
        this.review = review;
    }

    public Rating(Date date, Double score, Routine routine, String review, int id) {
        this.date = date;
        this.score = score;
        this.routine = routine;
        this.review = review;
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Date date;

    private Double score;

    private Routine routine;

    private String review;

    private int id;

    public Date getDate(){
        return date;
    }

    public Double getScore(){
        return score;
    }

    public Routine getRoutine(){
        return routine;
    }

    public String getReview(){
        return review;
    }

    public int getId(){
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return id == rating.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}