package ar.com.edu.itba.hci_app.network.api.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("totalCount")
    @Expose
    private int totalCount;
    @SerializedName("orderBy")
    @Expose
    private String orderBy;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("results")
    @Expose
    private List<Routine> routines = null;
    @SerializedName("size")
    @Expose
    private int size;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("isLastPage")
    @Expose
    private boolean isLastPage;


    public Video() {
    }


    public Video(int totalCount, String orderBy, String direction, List<Routine> routines, int size, int page, boolean isLastPage) {
        super();
        this.totalCount = totalCount;
        this.orderBy = orderBy;
        this.direction = direction;
        this.routines = routines;
        this.size = size;
        this.page = page;
        this.isLastPage = isLastPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

}