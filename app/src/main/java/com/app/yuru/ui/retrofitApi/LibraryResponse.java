package com.app.yuru.ui.retrofitApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LibraryResponse {

    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("result")
    @Expose
    private Result result;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}
