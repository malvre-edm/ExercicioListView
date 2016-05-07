package com.malvre.exerciciolistview.utils;

import java.util.ArrayList;
import java.util.List;


public class ErrorHandler {
    private List<String> errors;

    public ErrorHandler() {
        this.errors = new ArrayList<String>();
    }

    public void add(String error) {
        this.errors.add(error);
    }

    public boolean hasError() {
        return this.errors.size() > 0;
    }

    public String getMessage() {
        StringBuffer sb = new StringBuffer();
        for (String error : errors) {
            sb.append(error).append("\n");
        }
        return sb.toString();
    }
}
