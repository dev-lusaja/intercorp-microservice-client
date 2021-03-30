package com.devlusaja.intercorpmicroserviceclient.presentation.response;

public class Response {
    Object data;
    String error;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
