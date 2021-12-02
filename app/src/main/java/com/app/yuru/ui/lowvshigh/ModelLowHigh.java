package com.app.yuru.ui.lowvshigh;

import com.android.volley.toolbox.StringRequest;

public class ModelLowHigh {
    private String id;
    private String question_text;
    private String question_title;
    private String low_data_point;
    private String high_data_point;
    private String question_category_id;
    private String created_at;
    private String updated_at;

    public ModelLowHigh(String id, String question_text, String question_title,
                        String low_data_point, String high_data_point, String question_category_id, String created_at, String updated_at) {
        this.id = id;
        this.question_text = question_text;
        this.question_title = question_title;
        this.low_data_point = low_data_point;
        this.high_data_point = high_data_point;
        this.question_category_id = question_category_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getLow_data_point() {
        return low_data_point;
    }

    public void setLow_data_point(String low_data_point) {
        this.low_data_point = low_data_point;
    }

    public String getHigh_data_point() {
        return high_data_point;
    }

    public void setHigh_data_point(String high_data_point) {
        this.high_data_point = high_data_point;
    }

    public String getQuestion_category_id() {
        return question_category_id;
    }

    public void setQuestion_category_id(String question_category_id) {
        this.question_category_id = question_category_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
