package com.app.yuru.ui.test;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class ModelAnswers {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("answer")
    @Expose
    private JSONArray answer;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public JSONArray getAnswer() {
        return answer;
    }

    public void setAnswer(JSONArray answer) {
        this.answer = answer;
    }
}
