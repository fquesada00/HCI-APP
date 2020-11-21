package ar.com.edu.itba.hci_app.ui.main;

public enum FilterEnum {
    CrationFilter(0), RatingFilter(1), DifficultyFilter(2), CategoryFilter(3), None(4);
    private int val;
    FilterEnum(int val){
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
