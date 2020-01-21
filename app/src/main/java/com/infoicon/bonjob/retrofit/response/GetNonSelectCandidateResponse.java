package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 31/8/17.
 */

public class GetNonSelectCandidateResponse {

    /**
     * success : true
     * error : false
     * data : []
     * msg : Archived Successfully
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<?> data;

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
