package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 6/11/17.
 */

public class GetSelectCandidateBySearchResponse {

    /**
     * success : true
     * error : false
     * data : []
     * msg : Your request has been sent successfully.
     * active_user : 0
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<?> data;

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

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
