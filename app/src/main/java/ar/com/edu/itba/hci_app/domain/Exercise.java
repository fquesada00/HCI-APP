package ar.com.edu.itba.hci_app.domain;


import java.io.Serializable;
import java.util.Objects;

public class Exercise implements Serializable {
    public int getCycleId() {
        return cycleId;
    }

    public Exercise(int duration, String name, int id, String detail, String type, int repetitions, int order, int cycleId) {
        this.duration = duration;
        this.name = name;
        this.id = id;
        this.detail = detail;
        this.type = type;
        this.repetitions = repetitions;
        this.order = order;
        this.cycleId = cycleId;
    }

    private int duration;

    private String name;

    private int id;

    private String detail;

    private String type;

    private int repetitions;

    private int order;

    private int cycleId;

    public int getDuration(){
        return duration;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public String getDetail(){
        return detail;
    }

    public String getType(){
        return type;
    }

    public int getRepetitions(){
        return repetitions;
    }

    public int getOrder(){
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return id == exercise.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
