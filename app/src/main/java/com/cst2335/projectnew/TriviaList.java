package com.cst2335.projectnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TriviaList {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    @SerializedName("results")
    @Expose
    private List<Trivia> results = null;

    public Integer getResponseCode(){
        return responseCode;
    }

    public List<Trivia> getResults(){
        return results;
    }
}
