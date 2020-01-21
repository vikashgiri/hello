package com.infoicon.bonjob.retrofit.response;

import java.util.List;

/**
 * Created by infoicona on 17/8/17.
 */

public class GetHireResponse {

    /**
     * success : true
     * error : false
     * data : []
     * msg : Candidate Hired Successfully
     */

    private boolean success;
    private boolean error;
    private String msg;
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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
