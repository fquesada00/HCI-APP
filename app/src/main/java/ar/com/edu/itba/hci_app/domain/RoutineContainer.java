package ar.com.edu.itba.hci_app.domain;

import java.util.List;

public class RoutineContainer {

    private List<Routine> list;

    public RoutineContainer(List<Routine> list) {
        this.list = list;
    }

    public RoutineContainer(){

    }

    public List<Routine> getList() {
        return list;
    }

    public void setList(List<Routine> list) {
        this.list = list;
    }
}
