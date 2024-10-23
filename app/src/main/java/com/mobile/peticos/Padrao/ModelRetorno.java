package com.mobile.peticos.Padrao;

public class ModelRetorno {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ModelRetorno(String message) {
        this.message = message;

    }

    @Override
    public String toString() {
        return "ModelRetorno{" +
                "message='" + message + '\'' +
                '}';
    }
}
