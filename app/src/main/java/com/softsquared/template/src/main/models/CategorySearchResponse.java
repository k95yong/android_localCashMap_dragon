package com.softsquared.template.src.main.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CategorySearchResponse implements Serializable {
    @SerializedName("isSuccess")
    public boolean isSuccess;
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
    @SerializedName("result")
    @Expose
    public ArrayList<SearchResult> results;
    @SerializedName("result2")
    @Expose
    public ArrayList<SearchResult> results2;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SearchResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchResult> results) {
        this.results = results;
    }

    public ArrayList<SearchResult> getResults2() {
        return results2;
    }

    public void setResults2(ArrayList<SearchResult> results2) {
        this.results2 = results2;
    }
}
