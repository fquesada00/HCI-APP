package ar.com.edu.itba.hci_app.network;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ar.com.edu.itba.hci_app.network.api.model.Error;

import static ar.com.edu.itba.hci_app.network.Status.ERROR;
import static ar.com.edu.itba.hci_app.network.Status.SUCCESS;
import static ar.com.edu.itba.hci_app.network.Status.LOADING;

public class Resource<T> {

    @NonNull
    private final Status status;

    @Nullable
    private final Error error;

    @Nullable
    private final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Error error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Error error, @Nullable T data) {
        return new Resource<>(ERROR, data, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public Error getError() {
        return error;
    }

    @Nullable
    public T getData() {
        return data;
    }
}
